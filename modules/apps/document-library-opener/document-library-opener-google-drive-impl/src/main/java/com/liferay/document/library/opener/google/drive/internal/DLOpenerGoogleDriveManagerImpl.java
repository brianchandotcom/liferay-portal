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

package com.liferay.document.library.opener.google.drive.internal;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;

import com.liferay.document.library.opener.google.drive.DLOpenerGoogleDriveFileReference;
import com.liferay.document.library.opener.google.drive.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.google.drive.constants.DLOpenerGoogleDriveMimeTypes;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.security.GeneralSecurityException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = DLOpenerGoogleDriveManager.class)
public class DLOpenerGoogleDriveManagerImpl
	implements DLOpenerGoogleDriveManager {

	@Override
	public DLOpenerGoogleDriveFileReference checkOut(
			long userId, FileEntry fileEntry)
		throws PortalException {

		try {
			com.google.api.services.drive.model.File file =
				new com.google.api.services.drive.model.File();

			file.setMimeType(
				DLOpenerGoogleDriveMimeTypes.
					APPLICATION_VND_GOOGLE_APPS_DOCUMENT);
			file.setName(fileEntry.getTitle());

			FileContent fileContent = new FileContent(
				fileEntry.getMimeType(), _getFileEntryFile(fileEntry));

			Drive drive = new Drive.Builder(
				_netHttpTransport, _jsonFactory, _getCredential(userId)
			).build();

			Drive.Files driveFiles = drive.files();

			Drive.Files.Create driveFilesCreate = driveFiles.create(
				file, fileContent);

			com.google.api.services.drive.model.File uploadedFile =
				driveFilesCreate.execute();

			_dlOpenerFileEntryReferenceLocalService.
				addDLOpenerFileEntryReference(
					userId, uploadedFile.getId(), fileEntry);

			return new DLOpenerGoogleDriveFileReference(
				uploadedFile.getId(), fileEntry.getFileEntryId(),
				fileEntry.getTitle(), () -> _getContentFile(userId, fileEntry));
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	@Override
	public DLOpenerGoogleDriveFileReference create(
			long userId, FileEntry fileEntry)
		throws PortalException {

		try {
			com.google.api.services.drive.model.File file =
				new com.google.api.services.drive.model.File();

			file.setMimeType(
				DLOpenerGoogleDriveMimeTypes.
					APPLICATION_VND_GOOGLE_APPS_DOCUMENT);
			file.setName(fileEntry.getTitle());

			Drive drive = new Drive.Builder(
				_netHttpTransport, _jsonFactory, _getCredential(userId)
			).build();

			Drive.Files driveFiles = drive.files();

			Drive.Files.Create driveFilesCreate = driveFiles.create(file);

			com.google.api.services.drive.model.File uploadedFile =
				driveFilesCreate.execute();

			_dlOpenerFileEntryReferenceLocalService.
				addDLOpenerFileEntryReference(
					userId, uploadedFile.getId(), fileEntry);

			return new DLOpenerGoogleDriveFileReference(
				uploadedFile.getId(), fileEntry.getFileEntryId(),
				fileEntry.getTitle(), () -> _getContentFile(userId, fileEntry));
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	@Override
	public void delete(long userId, FileEntry fileEntry)
		throws PortalException {

		try {
			Drive drive = new Drive.Builder(
				_netHttpTransport, _jsonFactory, _getCredential(userId)
			).build();

			Drive.Files driveFiles = drive.files();

			Drive.Files.Delete driveFilesDelete = driveFiles.delete(
				_getGoogleDriveFileId(fileEntry));

			driveFilesDelete.execute();

			_dlOpenerFileEntryReferenceLocalService.
				deleteDLOpenerFileEntryReference(fileEntry);
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	@Override
	public String getAuthorizationURL(String state, String redirectUri) {
		return _oAuth2Manager.getAuthorizationURL(state, redirectUri);
	}

	private File _getContentFile(long userId, FileEntry fileEntry) {
		try {
			Drive drive = new Drive.Builder(
				_netHttpTransport, _jsonFactory, _getCredential(userId)
			).build();

			Drive.Files driveFiles = drive.files();

			Drive.Files.Export driveFilesExport = driveFiles.export(
				_getGoogleDriveFileId(fileEntry), fileEntry.getMimeType());

			try (InputStream is =
					driveFilesExport.executeMediaAsInputStream()) {

				return FileUtil.createTempFile(is);
			}
		}
		catch (IOException | PortalException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean hasValidCredential(long userId) throws IOException {
		Credential credential = _oAuth2Manager.getCredential(userId);

		if (credential == null) {
			return false;
		}

		if ((credential.getExpiresInSeconds() <= 0) &&
			!credential.refreshToken()) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isGoogleDriveFile(FileEntry fileEntry) {
		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				fetchDLOpenerFileEntryReference(fileEntry);

		if (dlOpenerFileEntryReference == null) {
			return false;
		}

		return true;
	}

	@Override
	public void requestAuthorizationToken(
			long userId, String code, String redirectUri)
		throws IOException {

		_oAuth2Manager.requestAuthorizationToken(userId, code, redirectUri);
	}

	@Override
	public DLOpenerGoogleDriveFileReference requestEditAccess(
			long userId, FileEntry fileEntry)
		throws PortalException {

		try {
			String googleDriveFileId = _getGoogleDriveFileId(fileEntry);

			if (Validator.isNull(googleDriveFileId)) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"File entry ", fileEntry.getFileEntryId(),
						" is not a Google Drive file"));
			}

			_checkCredential(userId);

			return new DLOpenerGoogleDriveFileReference(
				googleDriveFileId, fileEntry.getFileEntryId(),
				fileEntry.getTitle(), () -> _getContentFile(userId, fileEntry));
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	@Activate
	protected void activate() throws GeneralSecurityException, IOException {
		_jsonFactory = JacksonFactory.getDefaultInstance();
		_netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
	}

	private void _checkCredential(long userId)
		throws IOException, PrincipalException {

		_getCredential(userId);
	}

	private Credential _getCredential(long userId)
		throws IOException, PrincipalException {

		Credential credential = _oAuth2Manager.getCredential(userId);

		if (credential == null) {
			throw new PrincipalException(
				StringBundler.concat(
					"User ", userId,
					" does not have a valid Google credential"));
		}

		return credential;
	}

	private File _getFileEntryFile(FileEntry fileEntry)
		throws IOException, PortalException {

		try (InputStream is = fileEntry.getContentStream()) {
			return FileUtil.createTempFile(is);
		}
	}

	private String _getGoogleDriveFileId(FileEntry fileEntry)
		throws PortalException {

		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				getDLOpenerFileEntryReference(fileEntry);

		return dlOpenerFileEntryReference.getReferenceKey();
	}

	@Reference
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

	private JsonFactory _jsonFactory;
	private NetHttpTransport _netHttpTransport;

	@Reference
	private OAuth2Manager _oAuth2Manager;

}