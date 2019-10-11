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

package com.liferay.document.library.opener.google.drive.web.internal;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;

import com.liferay.document.library.opener.constants.DLOpenerFileEntryReferenceConstants;
import com.liferay.document.library.opener.google.drive.web.internal.background.task.UploadGoogleDriveDocumentBackgroundTaskExecutor;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveConstants;
import com.liferay.document.library.opener.google.drive.web.internal.constants.GoogleDriveBackgroundTaskConstants;
import com.liferay.document.library.opener.google.drive.web.internal.oauth.OAuth2Manager;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

import java.security.GeneralSecurityException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	service = {
		com.liferay.document.library.opener.google.drive.
			DLOpenerGoogleDriveManager.class,
		DLOpenerGoogleDriveManager.class
	}
)
public class DLOpenerGoogleDriveManager
	implements com.liferay.document.library.opener.google.drive.
				   DLOpenerGoogleDriveManager {

	@Override
	public DLOpenerGoogleDriveFileReference checkOut(
			long userId, FileEntry fileEntry)
		throws PortalException {

		_dlOpenerFileEntryReferenceLocalService.
			addPlaceholderDLOpenerFileEntryReference(
				userId,
				DLOpenerGoogleDriveConstants.GOOGLE_DRIVE_REFERENCE_TYPE,
				fileEntry, DLOpenerFileEntryReferenceConstants.TYPE_EDIT);

		BackgroundTask backgroundTask = _addBackgroundTask(
			GoogleDriveBackgroundTaskConstants.CHECKOUT, fileEntry, userId);

		return new DLOpenerGoogleDriveFileReference(
			fileEntry.getFileEntryId(),
			new CachingSupplier<>(
				() -> _getGoogleDriveFileTitle(userId, fileEntry)),
			() -> _getContentFile(userId, fileEntry),
			backgroundTask.getBackgroundTaskId());
	}

	@Override
	public DLOpenerGoogleDriveFileReference create(
			long userId, FileEntry fileEntry)
		throws PortalException {

		_dlOpenerFileEntryReferenceLocalService.
			addPlaceholderDLOpenerFileEntryReference(
				userId,
				DLOpenerGoogleDriveConstants.GOOGLE_DRIVE_REFERENCE_TYPE,
				fileEntry, DLOpenerFileEntryReferenceConstants.TYPE_NEW);

		BackgroundTask backgroundTask = _addBackgroundTask(
			GoogleDriveBackgroundTaskConstants.CREATE, fileEntry, userId);

		return new DLOpenerGoogleDriveFileReference(
			fileEntry.getFileEntryId(),
			new CachingSupplier<>(
				() -> _getGoogleDriveFileTitle(userId, fileEntry)),
			() -> _getContentFile(userId, fileEntry),
			backgroundTask.getBackgroundTaskId());
	}

	@Override
	public void delete(long userId, FileEntry fileEntry)
		throws PortalException {

		try {
			Drive drive = new Drive.Builder(
				_netHttpTransport, _jsonFactory,
				_getCredential(fileEntry.getCompanyId(), userId)
			).build();

			Drive.Files driveFiles = drive.files();

			Drive.Files.Delete driveFilesDelete = driveFiles.delete(
				_getGoogleDriveFileId(fileEntry));

			driveFilesDelete.execute();

			_dlOpenerFileEntryReferenceLocalService.
				deleteDLOpenerFileEntryReference(
					DLOpenerGoogleDriveConstants.GOOGLE_DRIVE_REFERENCE_TYPE,
					fileEntry);
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	@Override
	public String getAuthorizationURL(
			long companyId, String state, String redirectUri)
		throws PortalException {

		return _oAuth2Manager.getAuthorizationURL(
			companyId, state, redirectUri);
	}

	@Override
	public boolean hasValidCredential(long companyId, long userId)
		throws IOException, PortalException {

		Credential credential = _oAuth2Manager.getCredential(companyId, userId);

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
	public boolean isConfigured(long companyId) {
		return _oAuth2Manager.isConfigured(companyId);
	}

	@Override
	public boolean isGoogleDriveFile(FileEntry fileEntry) {
		return Optional.ofNullable(
			_dlOpenerFileEntryReferenceLocalService.
				fetchDLOpenerFileEntryReference(
					DLOpenerGoogleDriveConstants.GOOGLE_DRIVE_REFERENCE_TYPE,
					fileEntry)
		).map(
			dlOpenerFileEntryReference -> true
		).orElse(
			false
		);
	}

	@Override
	public void requestAuthorizationToken(
			long companyId, long userId, String code, String redirectUri)
		throws IOException, PortalException {

		_oAuth2Manager.requestAuthorizationToken(
			companyId, userId, code, redirectUri);
	}

	@Override
	public DLOpenerGoogleDriveFileReference requestEditAccess(
			long userId, FileEntry fileEntry)
		throws PortalException {

		String googleDriveFileId = _getGoogleDriveFileId(fileEntry);

		if (Validator.isNull(googleDriveFileId)) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"File entry ", fileEntry.getFileEntryId(),
					" is not a Google Drive file"));
		}

		_checkCredential(fileEntry.getCompanyId(), userId);

		return new DLOpenerGoogleDriveFileReference(
			fileEntry.getFileEntryId(),
			new CachingSupplier<>(
				() -> _getGoogleDriveFileTitle(userId, fileEntry)),
			() -> _getContentFile(userId, fileEntry), 0);
	}

	@Override
	public void setAuthorizationToken(
			long companyId, long userId, String authorizationToken)
		throws IOException, PortalException {

		_oAuth2Manager.setAccessToken(companyId, userId, authorizationToken);
	}

	@Activate
	protected void activate() throws GeneralSecurityException, IOException {
		_jsonFactory = JacksonFactory.getDefaultInstance();
		_netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
	}

	private BackgroundTask _addBackgroundTask(
			String cmd, FileEntry fileEntry, long userId)
		throws PortalException {

		Map<String, Serializable> taskContextMap = new HashMap<>();

		taskContextMap.put(
			BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS, true);
		taskContextMap.put(GoogleDriveBackgroundTaskConstants.CMD, cmd);
		taskContextMap.put(
			GoogleDriveBackgroundTaskConstants.COMPANY_ID,
			fileEntry.getCompanyId());
		taskContextMap.put(
			GoogleDriveBackgroundTaskConstants.FILE_ENTRY_ID,
			fileEntry.getFileEntryId());
		taskContextMap.put(GoogleDriveBackgroundTaskConstants.USER_ID, userId);

		return _backgroundTaskManager.addBackgroundTask(
			userId, CompanyConstants.SYSTEM,
			StringBundler.concat(
				DLOpenerGoogleDriveManager.class.getSimpleName(),
				StringPool.POUND, fileEntry.getFileEntryId()),
			UploadGoogleDriveDocumentBackgroundTaskExecutor.class.getName(),
			taskContextMap, new ServiceContext());
	}

	private void _checkCredential(long companyId, long userId)
		throws PortalException {

		_getCredential(companyId, userId);
	}

	private File _getContentFile(long userId, FileEntry fileEntry) {
		try {
			Credential credential = _getCredential(
				fileEntry.getCompanyId(), userId);

			Drive drive = new Drive.Builder(
				_netHttpTransport, _jsonFactory, credential
			).build();

			Drive.Files driveFiles = drive.files();

			Drive.Files.Get get = driveFiles.get(
				_getGoogleDriveFileId(fileEntry));

			get.setFields("exportLinks");

			com.google.api.services.drive.model.File file = get.execute();

			Map<String, String> exportLinks = file.getExportLinks();

			URL url = new URL(exportLinks.get(fileEntry.getMimeType()));

			if (!StringUtil.startsWith(url.getProtocol(), Http.HTTP)) {
				throw new SecurityException(
					"Only HTTP links are allowed: " + url);
			}

			if (InetAddressUtil.isLocalInetAddress(
					InetAddress.getByName(url.getHost()))) {

				throw new SecurityException(
					"Local links are not allowed: " + url);
			}

			URLConnection urlConnection = url.openConnection();

			urlConnection.setRequestProperty(
				"Authorization", "Bearer " + credential.getAccessToken());

			try (InputStream is = urlConnection.getInputStream()) {
				return FileUtil.createTempFile(is);
			}
		}
		catch (IOException | PortalException e) {
			throw new RuntimeException(e);
		}
	}

	private Credential _getCredential(long companyId, long userId)
		throws PortalException {

		Credential credential = _oAuth2Manager.getCredential(companyId, userId);

		if (credential == null) {
			throw new PrincipalException(
				StringBundler.concat(
					"User ", userId,
					" does not have a valid Google credential"));
		}

		return credential;
	}

	private String _getGoogleDriveFileId(FileEntry fileEntry)
		throws PortalException {

		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				getDLOpenerFileEntryReference(
					DLOpenerGoogleDriveConstants.GOOGLE_DRIVE_REFERENCE_TYPE,
					fileEntry);

		return dlOpenerFileEntryReference.getReferenceKey();
	}

	private String _getGoogleDriveFileTitle(long userId, FileEntry fileEntry) {
		try {
			Drive drive = new Drive.Builder(
				_netHttpTransport, _jsonFactory,
				_getCredential(fileEntry.getCompanyId(), userId)
			).build();

			Drive.Files driveFiles = drive.files();

			Drive.Files.Get driveFilesGet = driveFiles.get(
				_getGoogleDriveFileId(fileEntry));

			com.google.api.services.drive.model.File file =
				driveFilesGet.execute();

			return file.getName();
		}
		catch (IOException | PortalException e) {
			throw new RuntimeException(e);
		}
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

	private JsonFactory _jsonFactory;
	private NetHttpTransport _netHttpTransport;

	@Reference
	private OAuth2Manager _oAuth2Manager;

	private static class CachingSupplier<T> implements Supplier<T> {

		public CachingSupplier(Supplier<T> supplier) {
			_supplier = supplier;
		}

		@Override
		public T get() {
			if (_value != null) {
				return _value;
			}

			_value = _supplier.get();

			return _value;
		}

		private final Supplier<T> _supplier;
		private T _value;

	}

}