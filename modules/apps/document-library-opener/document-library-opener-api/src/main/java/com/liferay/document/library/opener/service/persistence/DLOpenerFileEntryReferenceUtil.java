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

package com.liferay.document.library.opener.service.persistence;

import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
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
 * The persistence utility for the dl opener file entry reference service. This utility wraps <code>com.liferay.document.library.opener.service.persistence.impl.DLOpenerFileEntryReferencePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLOpenerFileEntryReferencePersistence
 * @generated
 */
public class DLOpenerFileEntryReferenceUtil {

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
	public static void clearCache(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {

		getPersistence().clearCache(dlOpenerFileEntryReference);
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
	public static Map<Serializable, DLOpenerFileEntryReference>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLOpenerFileEntryReference> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLOpenerFileEntryReference> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLOpenerFileEntryReference> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DLOpenerFileEntryReference> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DLOpenerFileEntryReference update(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {

		return getPersistence().update(dlOpenerFileEntryReference);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DLOpenerFileEntryReference update(
		DLOpenerFileEntryReference dlOpenerFileEntryReference,
		ServiceContext serviceContext) {

		return getPersistence().update(
			dlOpenerFileEntryReference, serviceContext);
	}

	/**
	 * Returns the dl opener file entry reference where fileEntryId = &#63; or throws a <code>NoSuchFileEntryReferenceException</code> if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference
	 * @throws NoSuchFileEntryReferenceException if a matching dl opener file entry reference could not be found
	 */
	public static DLOpenerFileEntryReference findByFileEntryId(long fileEntryId)
		throws com.liferay.document.library.opener.exception.
			NoSuchFileEntryReferenceException {

		return getPersistence().findByFileEntryId(fileEntryId);
	}

	/**
	 * Returns the dl opener file entry reference where fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	public static DLOpenerFileEntryReference fetchByFileEntryId(
		long fileEntryId) {

		return getPersistence().fetchByFileEntryId(fileEntryId);
	}

	/**
	 * Returns the dl opener file entry reference where fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	public static DLOpenerFileEntryReference fetchByFileEntryId(
		long fileEntryId, boolean useFinderCache) {

		return getPersistence().fetchByFileEntryId(fileEntryId, useFinderCache);
	}

	/**
	 * Removes the dl opener file entry reference where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the dl opener file entry reference that was removed
	 */
	public static DLOpenerFileEntryReference removeByFileEntryId(
			long fileEntryId)
		throws com.liferay.document.library.opener.exception.
			NoSuchFileEntryReferenceException {

		return getPersistence().removeByFileEntryId(fileEntryId);
	}

	/**
	 * Returns the number of dl opener file entry references where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching dl opener file entry references
	 */
	public static int countByFileEntryId(long fileEntryId) {
		return getPersistence().countByFileEntryId(fileEntryId);
	}

	/**
	 * Returns the dl opener file entry reference where referenceType = &#63; and fileEntryId = &#63; or throws a <code>NoSuchFileEntryReferenceException</code> if it could not be found.
	 *
	 * @param referenceType the reference type
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference
	 * @throws NoSuchFileEntryReferenceException if a matching dl opener file entry reference could not be found
	 */
	public static DLOpenerFileEntryReference findByR_F(
			String referenceType, long fileEntryId)
		throws com.liferay.document.library.opener.exception.
			NoSuchFileEntryReferenceException {

		return getPersistence().findByR_F(referenceType, fileEntryId);
	}

	/**
	 * Returns the dl opener file entry reference where referenceType = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param referenceType the reference type
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	public static DLOpenerFileEntryReference fetchByR_F(
		String referenceType, long fileEntryId) {

		return getPersistence().fetchByR_F(referenceType, fileEntryId);
	}

	/**
	 * Returns the dl opener file entry reference where referenceType = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param referenceType the reference type
	 * @param fileEntryId the file entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	public static DLOpenerFileEntryReference fetchByR_F(
		String referenceType, long fileEntryId, boolean useFinderCache) {

		return getPersistence().fetchByR_F(
			referenceType, fileEntryId, useFinderCache);
	}

	/**
	 * Removes the dl opener file entry reference where referenceType = &#63; and fileEntryId = &#63; from the database.
	 *
	 * @param referenceType the reference type
	 * @param fileEntryId the file entry ID
	 * @return the dl opener file entry reference that was removed
	 */
	public static DLOpenerFileEntryReference removeByR_F(
			String referenceType, long fileEntryId)
		throws com.liferay.document.library.opener.exception.
			NoSuchFileEntryReferenceException {

		return getPersistence().removeByR_F(referenceType, fileEntryId);
	}

	/**
	 * Returns the number of dl opener file entry references where referenceType = &#63; and fileEntryId = &#63;.
	 *
	 * @param referenceType the reference type
	 * @param fileEntryId the file entry ID
	 * @return the number of matching dl opener file entry references
	 */
	public static int countByR_F(String referenceType, long fileEntryId) {
		return getPersistence().countByR_F(referenceType, fileEntryId);
	}

	/**
	 * Caches the dl opener file entry reference in the entity cache if it is enabled.
	 *
	 * @param dlOpenerFileEntryReference the dl opener file entry reference
	 */
	public static void cacheResult(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {

		getPersistence().cacheResult(dlOpenerFileEntryReference);
	}

	/**
	 * Caches the dl opener file entry references in the entity cache if it is enabled.
	 *
	 * @param dlOpenerFileEntryReferences the dl opener file entry references
	 */
	public static void cacheResult(
		List<DLOpenerFileEntryReference> dlOpenerFileEntryReferences) {

		getPersistence().cacheResult(dlOpenerFileEntryReferences);
	}

	/**
	 * Creates a new dl opener file entry reference with the primary key. Does not add the dl opener file entry reference to the database.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key for the new dl opener file entry reference
	 * @return the new dl opener file entry reference
	 */
	public static DLOpenerFileEntryReference create(
		long dlOpenerFileEntryReferenceId) {

		return getPersistence().create(dlOpenerFileEntryReferenceId);
	}

	/**
	 * Removes the dl opener file entry reference with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference that was removed
	 * @throws NoSuchFileEntryReferenceException if a dl opener file entry reference with the primary key could not be found
	 */
	public static DLOpenerFileEntryReference remove(
			long dlOpenerFileEntryReferenceId)
		throws com.liferay.document.library.opener.exception.
			NoSuchFileEntryReferenceException {

		return getPersistence().remove(dlOpenerFileEntryReferenceId);
	}

	public static DLOpenerFileEntryReference updateImpl(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {

		return getPersistence().updateImpl(dlOpenerFileEntryReference);
	}

	/**
	 * Returns the dl opener file entry reference with the primary key or throws a <code>NoSuchFileEntryReferenceException</code> if it could not be found.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference
	 * @throws NoSuchFileEntryReferenceException if a dl opener file entry reference with the primary key could not be found
	 */
	public static DLOpenerFileEntryReference findByPrimaryKey(
			long dlOpenerFileEntryReferenceId)
		throws com.liferay.document.library.opener.exception.
			NoSuchFileEntryReferenceException {

		return getPersistence().findByPrimaryKey(dlOpenerFileEntryReferenceId);
	}

	/**
	 * Returns the dl opener file entry reference with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference, or <code>null</code> if a dl opener file entry reference with the primary key could not be found
	 */
	public static DLOpenerFileEntryReference fetchByPrimaryKey(
		long dlOpenerFileEntryReferenceId) {

		return getPersistence().fetchByPrimaryKey(dlOpenerFileEntryReferenceId);
	}

	/**
	 * Returns all the dl opener file entry references.
	 *
	 * @return the dl opener file entry references
	 */
	public static List<DLOpenerFileEntryReference> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the dl opener file entry references.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLOpenerFileEntryReferenceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl opener file entry references
	 * @param end the upper bound of the range of dl opener file entry references (not inclusive)
	 * @return the range of dl opener file entry references
	 */
	public static List<DLOpenerFileEntryReference> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the dl opener file entry references.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLOpenerFileEntryReferenceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl opener file entry references
	 * @param end the upper bound of the range of dl opener file entry references (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dl opener file entry references
	 */
	public static List<DLOpenerFileEntryReference> findAll(
		int start, int end,
		OrderByComparator<DLOpenerFileEntryReference> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dl opener file entry references.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLOpenerFileEntryReferenceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl opener file entry references
	 * @param end the upper bound of the range of dl opener file entry references (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dl opener file entry references
	 */
	public static List<DLOpenerFileEntryReference> findAll(
		int start, int end,
		OrderByComparator<DLOpenerFileEntryReference> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the dl opener file entry references from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of dl opener file entry references.
	 *
	 * @return the number of dl opener file entry references
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DLOpenerFileEntryReferencePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DLOpenerFileEntryReferencePersistence,
		 DLOpenerFileEntryReferencePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DLOpenerFileEntryReferencePersistence.class);

		ServiceTracker
			<DLOpenerFileEntryReferencePersistence,
			 DLOpenerFileEntryReferencePersistence> serviceTracker =
				new ServiceTracker
					<DLOpenerFileEntryReferencePersistence,
					 DLOpenerFileEntryReferencePersistence>(
						 bundle.getBundleContext(),
						 DLOpenerFileEntryReferencePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}