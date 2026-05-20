/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(List)
	 */
	public static void cacheResult(
		List<DLOpenerFileEntryReference> dlOpenerFileEntryReferences) {

		getPersistence().cacheResult(dlOpenerFileEntryReferences);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#cacheResult(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void cacheResult(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {

		getPersistence().cacheResult(dlOpenerFileEntryReference);
	}

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

	public static DLOpenerFileEntryReferencePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		DLOpenerFileEntryReferencePersistence persistence) {

		_persistence = persistence;
	}

	private static volatile DLOpenerFileEntryReferencePersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:2105915504