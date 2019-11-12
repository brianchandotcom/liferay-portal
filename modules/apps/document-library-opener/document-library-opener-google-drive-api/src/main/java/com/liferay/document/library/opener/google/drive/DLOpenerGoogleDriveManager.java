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

package com.liferay.document.library.opener.google.drive;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.IOException;

/**
 * Performs operations on a Google Drive file that is related (linked) to a
 * local {@code FileEntry}.
 *
 * <p>
 * When a file entry is linked to a Google Drive file, both represent the same
 * contents. The file entry keeps some state regarding its linked Google Drive
 * file. The methods in this interface automatically perform this linking and
 * unlinking. The links are represented by {@link
 * DLOpenerGoogleDriveFileReference}.
 * </p>
 *
 * @author     Adolfo Pérez
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public interface DLOpenerGoogleDriveManager {

	/**
	 * Creates a new file in Google Drive with the same content as a file entry,
	 * and returns a reference to that Google Drive file. The Google Drive
	 * file's ID is stored alongside the file entry's ID.
	 *
	 * <p>
	 * Note that this method does not check out the file entry in the portal. To
	 * do so, see {@code
	 * com.liferay.document.library.kernel.service.DLAppService#checkOutFileEntry(
	 * long, ServiceContext)}.
	 * </p>
	 *
	 * @param  userId the primary key of the user performing the operation
	 * @param  fileEntry the file entry
	 * @return the reference to the Google Drive file
	 * @throws PortalException if an error occurs while performing the operation
	 */
	public DLOpenerGoogleDriveFileReference checkOut(
			long userId, FileEntry fileEntry)
		throws PortalException;

	/**
	 * Creates a new empty Google Drive file that is linked to a file entry, and
	 * returns a reference to that Google Drive file.
	 *
	 * <p>
	 * This operation is similar to {@link #checkOut(long, FileEntry)}, but
	 * doesn't copy the file entry's content to Google Drive. When you know the
	 * file entry is empty (e.g., you're creating a new one), this operation is
	 * much more efficient than {@link #checkOut(long, FileEntry)}.
	 * </p>
	 *
	 * @param  userId the primary key of the user performing the operation
	 * @param  fileEntry the file entry
	 * @return the reference to the new Google Drive file
	 */
	public DLOpenerGoogleDriveFileReference create(
			long userId, FileEntry fileEntry)
		throws PortalException;

	/**
	 * Deletes the Google Drive file linked to a file entry. Note that this
	 * method doesn't delete the file entry; it only deletes the file in Google
	 * Drive.
	 *
	 * @param  userId the primary key of the user performing the operation
	 * @param  fileEntry the file entry
	 * @throws PortalException if a portal exception occurred
	 */
	public void delete(long userId, FileEntry fileEntry) throws PortalException;

	/**
	 * Returns the Google Drive authorization URL for use with OAuth 2.
	 *
	 * @param  companyId the ID of the company
	 * @param  state the user interaction's current state
	 * @param  redirectUri the URL to redirect to when authorization completes
	 * @return the authorization URL
	 * @review
	 */
	public String getAuthorizationURL(
			long companyId, String state, String redirectUri)
		throws PortalException;

	/**
	 * Returns {@code true} if the user has a valid credential. The credential
	 * is stored as part of the OAuth 2 authorization flow.
	 *
	 * @param  companyId
	 * @param  userId the primary key of the user
	 * @return {@code true} if the user has a valid credential; {@code false}
	 *         otherwise
	 * @throws IOException if an error occurs while checking for the credential
	 */
	public boolean hasValidCredential(long companyId, long userId)
		throws IOException, PortalException;

	/**
	 * Returns {@code true} if the Google Drive connection is configured. If
	 * this method returns {@code false}, the rest of the method's behavior is
	 * undefined.
	 *
	 * @param  companyId the ID of the company
	 * @return {@code true} if the Google Drive connection is configured; {@code
	 *         false} otherwise
	 * @review
	 */
	public boolean isConfigured(long companyId);

	/**
	 * Returns {@code true} if a file entry is linked to a Google Drive file.
	 *
	 * @param  fileEntry the file entry
	 * @return {@code true} if the file entry is linked to a Google Drive file;
	 *         {@code false} otherwise
	 */
	public boolean isGoogleDriveFile(FileEntry fileEntry);

	/**
	 * Requests an authorization token. This method should be used only as part
	 * of the OAuth 2 authorization flow with Google Drive.
	 *
	 * @param  companyId
	 * @param  userId the primary key of the user in the OAuth 2 authorization
	 *         flow
	 * @param  code the code received as part of the OAuth 2 authorization flow
	 * @param  redirectUri the redirect URI
	 * @throws IOException if an error occurs during the OAuth 2 authorization
	 *         flow
	 */
	public void requestAuthorizationToken(
			long companyId, long userId, String code, String redirectUri)
		throws IOException, PortalException;

	/**
	 * Requests Google Drive permissions to edit a file entry. If this request
	 * is sucessful, this method returns a reference to the Google Drive file.
	 *
	 * <p>
	 * For this method to succeed, the user must have completed the OAuth 2
	 * authorization flow and have a valid credential (see {@link
	 * #hasValidCredential(long, long)}).
	 * </p>
	 *
	 * @param  userId the primary key of the user requesting edit access
	 * @param  fileEntry the file entry
	 * @return the Google Drive file reference
	 * @throws PortalException if the user doesn't have permission to edit the
	 *         file
	 */
	public DLOpenerGoogleDriveFileReference requestEditAccess(
			long userId, FileEntry fileEntry)
		throws PortalException;

	/**
	 * Sets an authorization token. This method should be used to skip the OAuth
	 * 2 authorization flow with Google Drive if a valid token is already
	 * available.
	 *
	 * @param  companyId
	 * @param  userId the primary key of the user in the OAuth 2 authorization
	 *         flow
	 * @param  authorizationToken the authorization token
	 * @review
	 */
	public default void setAuthorizationToken(
			long companyId, long userId, String authorizationToken)
		throws IOException, PortalException {
	}

}