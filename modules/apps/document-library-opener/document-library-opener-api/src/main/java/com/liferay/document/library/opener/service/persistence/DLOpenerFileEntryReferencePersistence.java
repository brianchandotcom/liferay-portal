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

	/**
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
	 * Returns the dl opener file entry reference where fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	public DLOpenerFileEntryReference fetchByFileEntryId(long fileEntryId);

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
	 * Returns the dl opener file entry reference where referenceType = &#63; and fileEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param referenceType the reference type
	 * @param fileEntryId the file entry ID
	 * @return the matching dl opener file entry reference, or <code>null</code> if a matching dl opener file entry reference could not be found
	 */
	public DLOpenerFileEntryReference fetchByR_F(
		String referenceType, long fileEntryId);

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
	 * Caches the dl opener file entry reference in the entity cache if it is enabled.
	 *
	 * @param dlOpenerFileEntryReference the dl opener file entry reference
	 */
	public void cacheResult(
		DLOpenerFileEntryReference dlOpenerFileEntryReference);

	/**
	 * Caches the dl opener file entry references in the entity cache if it is enabled.
	 *
	 * @param dlOpenerFileEntryReferences the dl opener file entry references
	 */
	public void cacheResult(
		java.util.List<DLOpenerFileEntryReference> dlOpenerFileEntryReferences);

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
	 * Returns all the dl opener file entry references.
	 *
	 * @return the dl opener file entry references
	 */
	public java.util.List<DLOpenerFileEntryReference> findAll();

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
	public java.util.List<DLOpenerFileEntryReference> findAll(
		int start, int end);

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
	public java.util.List<DLOpenerFileEntryReference> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DLOpenerFileEntryReference> orderByComparator);

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
	public java.util.List<DLOpenerFileEntryReference> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<DLOpenerFileEntryReference> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the dl opener file entry references from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of dl opener file entry references.
	 *
	 * @return the number of dl opener file entry references
	 */
	public int countAll();

}