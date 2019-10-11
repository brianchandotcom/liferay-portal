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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.sync.model.SyncDLObject;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the sync dl object service. This utility wraps <code>com.liferay.sync.service.persistence.impl.SyncDLObjectPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLObjectPersistence
 * @generated
 */
public class SyncDLObjectUtil {

	/**
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
	public static void clearCache(SyncDLObject syncDLObject) {
		getPersistence().clearCache(syncDLObject);
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
	public static Map<Serializable, SyncDLObject> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SyncDLObject> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SyncDLObject> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SyncDLObject> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SyncDLObject update(SyncDLObject syncDLObject) {
		return getPersistence().update(syncDLObject);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SyncDLObject update(
		SyncDLObject syncDLObject, ServiceContext serviceContext) {

		return getPersistence().update(syncDLObject, serviceContext);
	}

	/**
	 * Returns all the sync dl objects where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @return the matching sync dl objects
	 */
	public static List<SyncDLObject> findByTreePath(String treePath) {
		return getPersistence().findByTreePath(treePath);
	}

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
	public static List<SyncDLObject> findByTreePath(
		String treePath, int start, int end) {

		return getPersistence().findByTreePath(treePath, start, end);
	}

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
	public static List<SyncDLObject> findByTreePath(
		String treePath, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findByTreePath(
			treePath, start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findByTreePath(
		String treePath, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByTreePath(
			treePath, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByTreePath_First(
			String treePath, OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByTreePath_First(
			treePath, orderByComparator);
	}

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByTreePath_First(
		String treePath, OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByTreePath_First(
			treePath, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByTreePath_Last(
			String treePath, OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByTreePath_Last(
			treePath, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByTreePath_Last(
		String treePath, OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByTreePath_Last(
			treePath, orderByComparator);
	}

	/**
	 * Returns the sync dl objects before and after the current sync dl object in the ordered set where treePath LIKE &#63;.
	 *
	 * @param syncDLObjectId the primary key of the current sync dl object
	 * @param treePath the tree path
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public static SyncDLObject[] findByTreePath_PrevAndNext(
			long syncDLObjectId, String treePath,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByTreePath_PrevAndNext(
			syncDLObjectId, treePath, orderByComparator);
	}

	/**
	 * Removes all the sync dl objects where treePath LIKE &#63; from the database.
	 *
	 * @param treePath the tree path
	 */
	public static void removeByTreePath(String treePath) {
		getPersistence().removeByTreePath(treePath);
	}

	/**
	 * Returns the number of sync dl objects where treePath LIKE &#63;.
	 *
	 * @param treePath the tree path
	 * @return the number of matching sync dl objects
	 */
	public static int countByTreePath(String treePath) {
		return getPersistence().countByTreePath(treePath);
	}

	/**
	 * Returns all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @return the matching sync dl objects
	 */
	public static List<SyncDLObject> findByM_R(
		long modifiedTime, long repositoryId) {

		return getPersistence().findByM_R(modifiedTime, repositoryId);
	}

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
	public static List<SyncDLObject> findByM_R(
		long modifiedTime, long repositoryId, int start, int end) {

		return getPersistence().findByM_R(
			modifiedTime, repositoryId, start, end);
	}

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
	public static List<SyncDLObject> findByM_R(
		long modifiedTime, long repositoryId, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findByM_R(
			modifiedTime, repositoryId, start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findByM_R(
		long modifiedTime, long repositoryId, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByM_R(
			modifiedTime, repositoryId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByM_R_First(
			long modifiedTime, long repositoryId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByM_R_First(
			modifiedTime, repositoryId, orderByComparator);
	}

	/**
	 * Returns the first sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByM_R_First(
		long modifiedTime, long repositoryId,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByM_R_First(
			modifiedTime, repositoryId, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByM_R_Last(
			long modifiedTime, long repositoryId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByM_R_Last(
			modifiedTime, repositoryId, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByM_R_Last(
		long modifiedTime, long repositoryId,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByM_R_Last(
			modifiedTime, repositoryId, orderByComparator);
	}

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
	public static SyncDLObject[] findByM_R_PrevAndNext(
			long syncDLObjectId, long modifiedTime, long repositoryId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByM_R_PrevAndNext(
			syncDLObjectId, modifiedTime, repositoryId, orderByComparator);
	}

	/**
	 * Removes all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; from the database.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 */
	public static void removeByM_R(long modifiedTime, long repositoryId) {
		getPersistence().removeByM_R(modifiedTime, repositoryId);
	}

	/**
	 * Returns the number of sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @return the number of matching sync dl objects
	 */
	public static int countByM_R(long modifiedTime, long repositoryId) {
		return getPersistence().countByM_R(modifiedTime, repositoryId);
	}

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching sync dl objects
	 */
	public static List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId) {

		return getPersistence().findByR_P(repositoryId, parentFolderId);
	}

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
	public static List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId, int start, int end) {

		return getPersistence().findByR_P(
			repositoryId, parentFolderId, start, end);
	}

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
	public static List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findByR_P(
			repositoryId, parentFolderId, start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findByR_P(
		long repositoryId, long parentFolderId, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_P(
			repositoryId, parentFolderId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByR_P_First(
			long repositoryId, long parentFolderId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_P_First(
			repositoryId, parentFolderId, orderByComparator);
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByR_P_First(
		long repositoryId, long parentFolderId,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByR_P_First(
			repositoryId, parentFolderId, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByR_P_Last(
			long repositoryId, long parentFolderId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_P_Last(
			repositoryId, parentFolderId, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByR_P_Last(
		long repositoryId, long parentFolderId,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByR_P_Last(
			repositoryId, parentFolderId, orderByComparator);
	}

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
	public static SyncDLObject[] findByR_P_PrevAndNext(
			long syncDLObjectId, long repositoryId, long parentFolderId,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_P_PrevAndNext(
			syncDLObjectId, repositoryId, parentFolderId, orderByComparator);
	}

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 */
	public static void removeByR_P(long repositoryId, long parentFolderId) {
		getPersistence().removeByR_P(repositoryId, parentFolderId);
	}

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and parentFolderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching sync dl objects
	 */
	public static int countByR_P(long repositoryId, long parentFolderId) {
		return getPersistence().countByR_P(repositoryId, parentFolderId);
	}

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the matching sync dl objects
	 */
	public static List<SyncDLObject> findByR_NotE(
		long repositoryId, String event) {

		return getPersistence().findByR_NotE(repositoryId, event);
	}

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
	public static List<SyncDLObject> findByR_NotE(
		long repositoryId, String event, int start, int end) {

		return getPersistence().findByR_NotE(repositoryId, event, start, end);
	}

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
	public static List<SyncDLObject> findByR_NotE(
		long repositoryId, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findByR_NotE(
			repositoryId, event, start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findByR_NotE(
		long repositoryId, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_NotE(
			repositoryId, event, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByR_NotE_First(
			long repositoryId, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_NotE_First(
			repositoryId, event, orderByComparator);
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByR_NotE_First(
		long repositoryId, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByR_NotE_First(
			repositoryId, event, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByR_NotE_Last(
			long repositoryId, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_NotE_Last(
			repositoryId, event, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByR_NotE_Last(
		long repositoryId, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByR_NotE_Last(
			repositoryId, event, orderByComparator);
	}

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
	public static SyncDLObject[] findByR_NotE_PrevAndNext(
			long syncDLObjectId, long repositoryId, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_NotE_PrevAndNext(
			syncDLObjectId, repositoryId, event, orderByComparator);
	}

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and event &ne; &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 */
	public static void removeByR_NotE(long repositoryId, String event) {
		getPersistence().removeByR_NotE(repositoryId, event);
	}

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the number of matching sync dl objects
	 */
	public static int countByR_NotE(long repositoryId, String event) {
		return getPersistence().countByR_NotE(repositoryId, event);
	}

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @return the matching sync dl objects
	 */
	public static List<SyncDLObject> findByR_T(long repositoryId, String type) {
		return getPersistence().findByR_T(repositoryId, type);
	}

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
	public static List<SyncDLObject> findByR_T(
		long repositoryId, String type, int start, int end) {

		return getPersistence().findByR_T(repositoryId, type, start, end);
	}

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
	public static List<SyncDLObject> findByR_T(
		long repositoryId, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findByR_T(
			repositoryId, type, start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findByR_T(
		long repositoryId, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_T(
			repositoryId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByR_T_First(
			long repositoryId, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_T_First(
			repositoryId, type, orderByComparator);
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByR_T_First(
		long repositoryId, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByR_T_First(
			repositoryId, type, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByR_T_Last(
			long repositoryId, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_T_Last(
			repositoryId, type, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByR_T_Last(
		long repositoryId, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByR_T_Last(
			repositoryId, type, orderByComparator);
	}

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
	public static SyncDLObject[] findByR_T_PrevAndNext(
			long syncDLObjectId, long repositoryId, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_T_PrevAndNext(
			syncDLObjectId, repositoryId, type, orderByComparator);
	}

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and type = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 */
	public static void removeByR_T(long repositoryId, String type) {
		getPersistence().removeByR_T(repositoryId, type);
	}

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param type the type
	 * @return the number of matching sync dl objects
	 */
	public static int countByR_T(long repositoryId, String type) {
		return getPersistence().countByR_T(repositoryId, type);
	}

	/**
	 * Returns all the sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @return the matching sync dl objects
	 */
	public static List<SyncDLObject> findByT_NotE(
		String treePath, String event) {

		return getPersistence().findByT_NotE(treePath, event);
	}

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
	public static List<SyncDLObject> findByT_NotE(
		String treePath, String event, int start, int end) {

		return getPersistence().findByT_NotE(treePath, event, start, end);
	}

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
	public static List<SyncDLObject> findByT_NotE(
		String treePath, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findByT_NotE(
			treePath, event, start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findByT_NotE(
		String treePath, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByT_NotE(
			treePath, event, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByT_NotE_First(
			String treePath, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByT_NotE_First(
			treePath, event, orderByComparator);
	}

	/**
	 * Returns the first sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByT_NotE_First(
		String treePath, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByT_NotE_First(
			treePath, event, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByT_NotE_Last(
			String treePath, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByT_NotE_Last(
			treePath, event, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByT_NotE_Last(
		String treePath, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByT_NotE_Last(
			treePath, event, orderByComparator);
	}

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
	public static SyncDLObject[] findByT_NotE_PrevAndNext(
			long syncDLObjectId, String treePath, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByT_NotE_PrevAndNext(
			syncDLObjectId, treePath, event, orderByComparator);
	}

	/**
	 * Removes all the sync dl objects where treePath LIKE &#63; and event &ne; &#63; from the database.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 */
	public static void removeByT_NotE(String treePath, String event) {
		getPersistence().removeByT_NotE(treePath, event);
	}

	/**
	 * Returns the number of sync dl objects where treePath LIKE &#63; and event &ne; &#63;.
	 *
	 * @param treePath the tree path
	 * @param event the event
	 * @return the number of matching sync dl objects
	 */
	public static int countByT_NotE(String treePath, String event) {
		return getPersistence().countByT_NotE(treePath, event);
	}

	/**
	 * Returns all the sync dl objects where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @return the matching sync dl objects
	 */
	public static List<SyncDLObject> findByV_T(String version, String type) {
		return getPersistence().findByV_T(version, type);
	}

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
	public static List<SyncDLObject> findByV_T(
		String version, String type, int start, int end) {

		return getPersistence().findByV_T(version, type, start, end);
	}

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
	public static List<SyncDLObject> findByV_T(
		String version, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findByV_T(
			version, type, start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findByV_T(
		String version, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByV_T(
			version, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByV_T_First(
			String version, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByV_T_First(
			version, type, orderByComparator);
	}

	/**
	 * Returns the first sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByV_T_First(
		String version, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByV_T_First(
			version, type, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByV_T_Last(
			String version, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByV_T_Last(
			version, type, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByV_T_Last(
		String version, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByV_T_Last(
			version, type, orderByComparator);
	}

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
	public static SyncDLObject[] findByV_T_PrevAndNext(
			long syncDLObjectId, String version, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByV_T_PrevAndNext(
			syncDLObjectId, version, type, orderByComparator);
	}

	/**
	 * Removes all the sync dl objects where version = &#63; and type = &#63; from the database.
	 *
	 * @param version the version
	 * @param type the type
	 */
	public static void removeByV_T(String version, String type) {
		getPersistence().removeByV_T(version, type);
	}

	/**
	 * Returns the number of sync dl objects where version = &#63; and type = &#63;.
	 *
	 * @param version the version
	 * @param type the type
	 * @return the number of matching sync dl objects
	 */
	public static int countByV_T(String version, String type) {
		return getPersistence().countByV_T(version, type);
	}

	/**
	 * Returns the sync dl object where type = &#63; and typePK = &#63; or throws a <code>NoSuchDLObjectException</code> if it could not be found.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the matching sync dl object
	 * @throws NoSuchDLObjectException if a matching sync dl object could not be found
	 */
	public static SyncDLObject findByT_T(String type, long typePK)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByT_T(type, typePK);
	}

	/**
	 * Returns the sync dl object where type = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByT_T(String type, long typePK) {
		return getPersistence().fetchByT_T(type, typePK);
	}

	/**
	 * Returns the sync dl object where type = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByT_T(
		String type, long typePK, boolean useFinderCache) {

		return getPersistence().fetchByT_T(type, typePK, useFinderCache);
	}

	/**
	 * Removes the sync dl object where type = &#63; and typePK = &#63; from the database.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the sync dl object that was removed
	 */
	public static SyncDLObject removeByT_T(String type, long typePK)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().removeByT_T(type, typePK);
	}

	/**
	 * Returns the number of sync dl objects where type = &#63; and typePK = &#63;.
	 *
	 * @param type the type
	 * @param typePK the type pk
	 * @return the number of matching sync dl objects
	 */
	public static int countByT_T(String type, long typePK) {
		return getPersistence().countByT_T(type, typePK);
	}

	/**
	 * Returns all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the matching sync dl objects
	 */
	public static List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String event) {

		return getPersistence().findByM_R_NotE(
			modifiedTime, repositoryId, event);
	}

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
	public static List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String event, int start,
		int end) {

		return getPersistence().findByM_R_NotE(
			modifiedTime, repositoryId, event, start, end);
	}

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
	public static List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findByM_R_NotE(
			modifiedTime, repositoryId, event, start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String event, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByM_R_NotE(
			modifiedTime, repositoryId, event, start, end, orderByComparator,
			useFinderCache);
	}

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
	public static SyncDLObject findByM_R_NotE_First(
			long modifiedTime, long repositoryId, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByM_R_NotE_First(
			modifiedTime, repositoryId, event, orderByComparator);
	}

	/**
	 * Returns the first sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByM_R_NotE_First(
		long modifiedTime, long repositoryId, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByM_R_NotE_First(
			modifiedTime, repositoryId, event, orderByComparator);
	}

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
	public static SyncDLObject findByM_R_NotE_Last(
			long modifiedTime, long repositoryId, String event,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByM_R_NotE_Last(
			modifiedTime, repositoryId, event, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByM_R_NotE_Last(
		long modifiedTime, long repositoryId, String event,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByM_R_NotE_Last(
			modifiedTime, repositoryId, event, orderByComparator);
	}

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
	public static SyncDLObject[] findByM_R_NotE_PrevAndNext(
			long syncDLObjectId, long modifiedTime, long repositoryId,
			String event, OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByM_R_NotE_PrevAndNext(
			syncDLObjectId, modifiedTime, repositoryId, event,
			orderByComparator);
	}

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
	public static List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String[] events) {

		return getPersistence().findByM_R_NotE(
			modifiedTime, repositoryId, events);
	}

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
	public static List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String[] events, int start,
		int end) {

		return getPersistence().findByM_R_NotE(
			modifiedTime, repositoryId, events, start, end);
	}

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
	public static List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String[] events, int start,
		int end, OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findByM_R_NotE(
			modifiedTime, repositoryId, events, start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findByM_R_NotE(
		long modifiedTime, long repositoryId, String[] events, int start,
		int end, OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByM_R_NotE(
			modifiedTime, repositoryId, events, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Removes all the sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63; from the database.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 */
	public static void removeByM_R_NotE(
		long modifiedTime, long repositoryId, String event) {

		getPersistence().removeByM_R_NotE(modifiedTime, repositoryId, event);
	}

	/**
	 * Returns the number of sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param event the event
	 * @return the number of matching sync dl objects
	 */
	public static int countByM_R_NotE(
		long modifiedTime, long repositoryId, String event) {

		return getPersistence().countByM_R_NotE(
			modifiedTime, repositoryId, event);
	}

	/**
	 * Returns the number of sync dl objects where modifiedTime &gt; &#63; and repositoryId = &#63; and event &ne; all &#63;.
	 *
	 * @param modifiedTime the modified time
	 * @param repositoryId the repository ID
	 * @param events the events
	 * @return the number of matching sync dl objects
	 */
	public static int countByM_R_NotE(
		long modifiedTime, long repositoryId, String[] events) {

		return getPersistence().countByM_R_NotE(
			modifiedTime, repositoryId, events);
	}

	/**
	 * Returns all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @return the matching sync dl objects
	 */
	public static List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type) {

		return getPersistence().findByR_P_T(repositoryId, parentFolderId, type);
	}

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
	public static List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type, int start,
		int end) {

		return getPersistence().findByR_P_T(
			repositoryId, parentFolderId, type, start, end);
	}

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
	public static List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findByR_P_T(
			repositoryId, parentFolderId, type, start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String type, int start, int end,
		OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_P_T(
			repositoryId, parentFolderId, type, start, end, orderByComparator,
			useFinderCache);
	}

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
	public static SyncDLObject findByR_P_T_First(
			long repositoryId, long parentFolderId, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_P_T_First(
			repositoryId, parentFolderId, type, orderByComparator);
	}

	/**
	 * Returns the first sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByR_P_T_First(
		long repositoryId, long parentFolderId, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByR_P_T_First(
			repositoryId, parentFolderId, type, orderByComparator);
	}

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
	public static SyncDLObject findByR_P_T_Last(
			long repositoryId, long parentFolderId, String type,
			OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_P_T_Last(
			repositoryId, parentFolderId, type, orderByComparator);
	}

	/**
	 * Returns the last sync dl object in the ordered set where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl object, or <code>null</code> if a matching sync dl object could not be found
	 */
	public static SyncDLObject fetchByR_P_T_Last(
		long repositoryId, long parentFolderId, String type,
		OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().fetchByR_P_T_Last(
			repositoryId, parentFolderId, type, orderByComparator);
	}

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
	public static SyncDLObject[] findByR_P_T_PrevAndNext(
			long syncDLObjectId, long repositoryId, long parentFolderId,
			String type, OrderByComparator<SyncDLObject> orderByComparator)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByR_P_T_PrevAndNext(
			syncDLObjectId, repositoryId, parentFolderId, type,
			orderByComparator);
	}

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
	public static List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types) {

		return getPersistence().findByR_P_T(
			repositoryId, parentFolderId, types);
	}

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
	public static List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types, int start,
		int end) {

		return getPersistence().findByR_P_T(
			repositoryId, parentFolderId, types, start, end);
	}

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
	public static List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types, int start,
		int end, OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findByR_P_T(
			repositoryId, parentFolderId, types, start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findByR_P_T(
		long repositoryId, long parentFolderId, String[] types, int start,
		int end, OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_P_T(
			repositoryId, parentFolderId, types, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Removes all the sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 */
	public static void removeByR_P_T(
		long repositoryId, long parentFolderId, String type) {

		getPersistence().removeByR_P_T(repositoryId, parentFolderId, type);
	}

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param type the type
	 * @return the number of matching sync dl objects
	 */
	public static int countByR_P_T(
		long repositoryId, long parentFolderId, String type) {

		return getPersistence().countByR_P_T(
			repositoryId, parentFolderId, type);
	}

	/**
	 * Returns the number of sync dl objects where repositoryId = &#63; and parentFolderId = &#63; and type = any &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param parentFolderId the parent folder ID
	 * @param types the types
	 * @return the number of matching sync dl objects
	 */
	public static int countByR_P_T(
		long repositoryId, long parentFolderId, String[] types) {

		return getPersistence().countByR_P_T(
			repositoryId, parentFolderId, types);
	}

	/**
	 * Caches the sync dl object in the entity cache if it is enabled.
	 *
	 * @param syncDLObject the sync dl object
	 */
	public static void cacheResult(SyncDLObject syncDLObject) {
		getPersistence().cacheResult(syncDLObject);
	}

	/**
	 * Caches the sync dl objects in the entity cache if it is enabled.
	 *
	 * @param syncDLObjects the sync dl objects
	 */
	public static void cacheResult(List<SyncDLObject> syncDLObjects) {
		getPersistence().cacheResult(syncDLObjects);
	}

	/**
	 * Creates a new sync dl object with the primary key. Does not add the sync dl object to the database.
	 *
	 * @param syncDLObjectId the primary key for the new sync dl object
	 * @return the new sync dl object
	 */
	public static SyncDLObject create(long syncDLObjectId) {
		return getPersistence().create(syncDLObjectId);
	}

	/**
	 * Removes the sync dl object with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object that was removed
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public static SyncDLObject remove(long syncDLObjectId)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().remove(syncDLObjectId);
	}

	public static SyncDLObject updateImpl(SyncDLObject syncDLObject) {
		return getPersistence().updateImpl(syncDLObject);
	}

	/**
	 * Returns the sync dl object with the primary key or throws a <code>NoSuchDLObjectException</code> if it could not be found.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object
	 * @throws NoSuchDLObjectException if a sync dl object with the primary key could not be found
	 */
	public static SyncDLObject findByPrimaryKey(long syncDLObjectId)
		throws com.liferay.sync.exception.NoSuchDLObjectException {

		return getPersistence().findByPrimaryKey(syncDLObjectId);
	}

	/**
	 * Returns the sync dl object with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param syncDLObjectId the primary key of the sync dl object
	 * @return the sync dl object, or <code>null</code> if a sync dl object with the primary key could not be found
	 */
	public static SyncDLObject fetchByPrimaryKey(long syncDLObjectId) {
		return getPersistence().fetchByPrimaryKey(syncDLObjectId);
	}

	/**
	 * Returns all the sync dl objects.
	 *
	 * @return the sync dl objects
	 */
	public static List<SyncDLObject> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<SyncDLObject> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<SyncDLObject> findAll(
		int start, int end, OrderByComparator<SyncDLObject> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<SyncDLObject> findAll(
		int start, int end, OrderByComparator<SyncDLObject> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the sync dl objects from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of sync dl objects.
	 *
	 * @return the number of sync dl objects
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SyncDLObjectPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SyncDLObjectPersistence, SyncDLObjectPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SyncDLObjectPersistence.class);

		ServiceTracker<SyncDLObjectPersistence, SyncDLObjectPersistence>
			serviceTracker =
				new ServiceTracker
					<SyncDLObjectPersistence, SyncDLObjectPersistence>(
						bundle.getBundleContext(),
						SyncDLObjectPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}