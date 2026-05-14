/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.service.persistence;

import com.liferay.document.library.opener.exception.NoSuchFileEntryReferenceException;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the dl opener file entry reference service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLOpenerFileEntryReferenceUtil
 * @generated
 */
@ProviderType
public interface DLOpenerFileEntryReferencePersistence
	extends BasePersistence<DLOpenerFileEntryReference> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLOpenerFileEntryReferenceUtil} to access the dl opener file entry reference persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the dl opener file entry reference where fileEntryId = &#63; or throws a <code>NoSuchFileEntryReferenceException</code> if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference
	 * @throws NoSuchFileEntryReferenceException if a matching dl opener file entry reference could not be found
	 */
	public DLOpenerFileEntryReference findByFileEntryId(long fileEntryId)
		throws NoSuchFileEntryReferenceException;

	/**
	 * Returns the dl opener file entry reference where fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	public DLOpenerFileEntryReference fetchByFileEntryId(
		long fileEntryId, boolean useFinderCache);

	/**
	 * Removes the dl opener file entry reference where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the dl opener file entry reference that was removed
	 */
	public DLOpenerFileEntryReference removeByFileEntryId(long fileEntryId)
		throws NoSuchFileEntryReferenceException;

	/**
	 * Returns the number of dl opener file entry references where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching dl opener file entry references
	 */
	public int countByFileEntryId(long fileEntryId);

	/**
	 * Returns the dl opener file entry reference where referenceType = &#63; and fileEntryId = &#63; or throws a <code>NoSuchFileEntryReferenceException</code> if it could not be found.
	 *
	 * @param referenceType the reference type
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference
	 * @throws NoSuchFileEntryReferenceException if a matching dl opener file entry reference could not be found
	 */
	public DLOpenerFileEntryReference findByR_F(
			String referenceType, long fileEntryId)
		throws NoSuchFileEntryReferenceException;

	/**
	 * Returns the dl opener file entry reference where referenceType = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param referenceType the reference type
	 * @param fileEntryId the file entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	public DLOpenerFileEntryReference fetchByR_F(
		String referenceType, long fileEntryId, boolean useFinderCache);

	/**
	 * Removes the dl opener file entry reference where referenceType = &#63; and fileEntryId = &#63; from the database.
	 *
	 * @param referenceType the reference type
	 * @param fileEntryId the file entry ID
	 * @return the dl opener file entry reference that was removed
	 */
	public DLOpenerFileEntryReference removeByR_F(
			String referenceType, long fileEntryId)
		throws NoSuchFileEntryReferenceException;

	/**
	 * Returns the number of dl opener file entry references where referenceType = &#63; and fileEntryId = &#63;.
	 *
	 * @param referenceType the reference type
	 * @param fileEntryId the file entry ID
	 * @return the number of matching dl opener file entry references
	 */
	public int countByR_F(String referenceType, long fileEntryId);

	/**
	 * Creates a new dl opener file entry reference with the primary key. Does not add the dl opener file entry reference to the database.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key for the new dl opener file entry reference
	 * @return the new dl opener file entry reference
	 */
	public DLOpenerFileEntryReference create(long dlOpenerFileEntryReferenceId);

	/**
	 * Removes the dl opener file entry reference with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference that was removed
	 * @throws NoSuchFileEntryReferenceException if a dl opener file entry reference with the primary key could not be found
	 */
	public DLOpenerFileEntryReference remove(long dlOpenerFileEntryReferenceId)
		throws NoSuchFileEntryReferenceException;

	public DLOpenerFileEntryReference updateImpl(
		DLOpenerFileEntryReference dlOpenerFileEntryReference);

	/**
	 * Returns the dl opener file entry reference with the primary key or throws a <code>NoSuchFileEntryReferenceException</code> if it could not be found.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference
	 * @throws NoSuchFileEntryReferenceException if a dl opener file entry reference with the primary key could not be found
	 */
	public DLOpenerFileEntryReference findByPrimaryKey(
			long dlOpenerFileEntryReferenceId)
		throws NoSuchFileEntryReferenceException;

	/**
	 * Returns the dl opener file entry reference with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dlOpenerFileEntryReferenceId the primary key of the dl opener file entry reference
	 * @return the dl opener file entry reference, or <code>null</code> if a dl opener file entry reference with the primary key could not be found
	 */
	public DLOpenerFileEntryReference fetchByPrimaryKey(
		long dlOpenerFileEntryReferenceId);

	/**
	 * Returns the dl opener file entry reference where fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	public default DLOpenerFileEntryReference fetchByFileEntryId(
		long fileEntryId) {

		return fetchByFileEntryId(fileEntryId, true);
	}

	/**
	 * Returns the dl opener file entry reference where referenceType = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param referenceType the reference type
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	public default DLOpenerFileEntryReference fetchByR_F(
		String referenceType, long fileEntryId) {

		return fetchByR_F(referenceType, fileEntryId, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:170903764