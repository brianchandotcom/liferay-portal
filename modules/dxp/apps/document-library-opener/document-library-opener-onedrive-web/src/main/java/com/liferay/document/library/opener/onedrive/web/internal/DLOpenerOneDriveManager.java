/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal;

import com.liferay.document.library.opener.constants.DLOpenerFileEntryReferenceConstants;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.onedrive.web.internal.background.task.UploadOneDriveDocumentBackgroundTaskExecutor;
import com.liferay.document.library.opener.onedrive.web.internal.configuration.DLOneDriveCompanyConfiguration;
import com.liferay.document.library.opener.onedrive.web.internal.constants.DLOpenerOneDriveConstants;
import com.liferay.document.library.opener.onedrive.web.internal.constants.OneDriveBackgroundTaskConstants;
import com.liferay.document.library.opener.onedrive.web.internal.exception.GraphServicePortalException;
import com.liferay.document.library.opener.onedrive.web.internal.exception.mapper.GraphServiceExceptionPortalExceptionMapper;
import com.liferay.document.library.opener.onedrive.web.internal.graph.IAuthenticationProviderImpl;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.AccessToken;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2Manager;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import com.microsoft.graph.core.DefaultClientConfig;
import com.microsoft.graph.http.GraphServiceException;
import com.microsoft.graph.models.extensions.DriveItem;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.models.extensions.Permission;
import com.microsoft.graph.models.extensions.SharingLink;
import com.microsoft.graph.models.extensions.User;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IDriveItemCreateLinkRequest;
import com.microsoft.graph.requests.extensions.IDriveItemRequest;
import com.microsoft.graph.requests.extensions.IDriveItemStreamRequest;
import com.microsoft.graph.requests.extensions.IUserRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Locale;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = DLOpenerOneDriveManager.class)
public class DLOpenerOneDriveManager {

	public DLOpenerOneDriveFileReference checkOut(
			long userId, FileEntry fileEntry, Locale locale)
		throws PortalException {

		BackgroundTask backgroundTask = _addBackgroundTask(
			userId, fileEntry, locale);

		_dlOpenerFileEntryReferenceLocalService.
			addPlaceholderDLOpenerFileEntryReference(
				userId, DLOpenerOneDriveConstants.ONE_DRIVE_REFERENCE_TYPE,
				fileEntry, DLOpenerFileEntryReferenceConstants.TYPE_EDIT);

		return new DLOpenerOneDriveFileReference<>(
			fileEntry.getFileEntryId(),
			new CachingUnsafeSupplier<>(
				() -> _getOneDriveFileTitle(userId, fileEntry)),
			() -> _getContentFile(userId, fileEntry),
			backgroundTask.getBackgroundTaskId());
	}

	public DLOpenerOneDriveFileReference createFile(
			long userId, FileEntry fileEntry, Locale locale)
		throws PortalException {

		BackgroundTask backgroundTask = _addBackgroundTask(
			userId, fileEntry, locale);

		_dlOpenerFileEntryReferenceLocalService.
			addPlaceholderDLOpenerFileEntryReference(
				userId, DLOpenerOneDriveConstants.ONE_DRIVE_REFERENCE_TYPE,
				fileEntry, DLOpenerFileEntryReferenceConstants.TYPE_NEW);

		return new DLOpenerOneDriveFileReference<>(
			fileEntry.getFileEntryId(),
			new CachingUnsafeSupplier<>(
				() -> _getOneDriveFileTitle(userId, fileEntry)),
			() -> _getContentFile(userId, fileEntry),
			backgroundTask.getBackgroundTaskId());
	}

	public void deleteFile(long userId, FileEntry fileEntry)
		throws PortalException {

		AccessToken accessToken = _getAccessToken(
			fileEntry.getCompanyId(), userId);

		IGraphServiceClient iGraphServiceClientBuilder =
			GraphServiceClient.fromConfig(
				DefaultClientConfig.createWithAuthenticationProvider(
					new IAuthenticationProviderImpl(accessToken)));

		IDriveItemRequest iDriveItemRequest = iGraphServiceClientBuilder.me(
		).drive(
		).items(
			_getOneDriveReferenceKey(fileEntry)
		).buildRequest();

		try {
			iDriveItemRequest.delete();

			_dlOpenerFileEntryReferenceLocalService.
				deleteDLOpenerFileEntryReference(
					DLOpenerOneDriveConstants.ONE_DRIVE_REFERENCE_TYPE,
					fileEntry);
		}
		catch (GraphServiceException graphServiceException) {
			GraphServicePortalException graphServicePortalException =
				GraphServiceExceptionPortalExceptionMapper.map(
					graphServiceException);

			if (graphServicePortalException instanceof
					GraphServicePortalException.ItemNotFound) {

				_dlOpenerFileEntryReferenceLocalService.
					deleteDLOpenerFileEntryReference(
						DLOpenerOneDriveConstants.ONE_DRIVE_REFERENCE_TYPE,
						fileEntry);
			}

			throw graphServicePortalException;
		}
	}

	public DLOpenerOneDriveFileReference getDLOpenerOneDriveFileReference(
			long userId, FileEntry fileEntry)
		throws PortalException {

		if (Validator.isNull(_getOneDriveFileId(fileEntry))) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"File entry ", fileEntry.getFileEntryId(),
					" is not a OneDrive file"));
		}

		_getAccessToken(fileEntry.getCompanyId(), userId);

		return new DLOpenerOneDriveFileReference<>(
			fileEntry.getFileEntryId(),
			new CachingUnsafeSupplier<>(
				() -> _getOneDriveFileTitle(userId, fileEntry)),
			() -> _getContentFile(userId, fileEntry));
	}

	public String getOneDriveFileURL(long userId, FileEntry fileEntry)
		throws PortalException {

		AccessToken accessToken = _getAccessToken(
			fileEntry.getCompanyId(), userId);

		IGraphServiceClient iGraphServiceClientBuilder =
			GraphServiceClient.fromConfig(
				DefaultClientConfig.createWithAuthenticationProvider(
					new IAuthenticationProviderImpl(accessToken)));

		IDriveItemCreateLinkRequest iDriveItemCreateLinkRequest =
			iGraphServiceClientBuilder.me(
			).drive(
			).items(
				_getOneDriveReferenceKey(fileEntry)
			).createLink(
				"edit", "organization"
			).buildRequest();

		try {
			Permission permission = iDriveItemCreateLinkRequest.post();

			SharingLink sharingLink = permission.link;

			return sharingLink.webUrl;
		}
		catch (GraphServiceException graphServiceException) {
			throw GraphServiceExceptionPortalExceptionMapper.map(
				graphServiceException);
		}
	}

	public User getUser(AccessToken accessToken) {
		IGraphServiceClient iGraphServiceClientBuilder =
			GraphServiceClient.fromConfig(
				DefaultClientConfig.createWithAuthenticationProvider(
					new IAuthenticationProviderImpl(accessToken)));

		IUserRequest iUserRequest = iGraphServiceClientBuilder.me(
		).buildRequest();

		return iUserRequest.get();
	}

	public boolean isConfigured(long companyId) throws ConfigurationException {
		DLOneDriveCompanyConfiguration dlOneDriveCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				DLOneDriveCompanyConfiguration.class, companyId);

		if (Validator.isNotNull(dlOneDriveCompanyConfiguration.clientId()) &&
			Validator.isNotNull(
				dlOneDriveCompanyConfiguration.clientSecret()) &&
			Validator.isNotNull(dlOneDriveCompanyConfiguration.tenant())) {

			return true;
		}

		return false;
	}

	public boolean isOneDriveFile(FileEntry fileEntry) {
		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				fetchDLOpenerFileEntryReference(
					DLOpenerOneDriveConstants.ONE_DRIVE_REFERENCE_TYPE,
					fileEntry);

		if (dlOpenerFileEntryReference != null) {
			return true;
		}

		return false;
	}

	public DLOpenerOneDriveFileReference requestEditAccess(
			long userId, FileEntry fileEntry)
		throws PortalException {

		if (Validator.isNull(_getOneDriveFileId(fileEntry))) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"File entry ", fileEntry.getFileEntryId(),
					" is not a OneDrive file"));
		}

		_getAccessToken(fileEntry.getCompanyId(), userId);

		return new DLOpenerOneDriveFileReference<>(
			fileEntry.getFileEntryId(),
			new CachingUnsafeSupplier<>(
				() -> _getOneDriveFileTitle(userId, fileEntry)),
			() -> _getContentFile(userId, fileEntry));
	}

	private BackgroundTask _addBackgroundTask(
			long userId, FileEntry fileEntry, Locale locale)
		throws PortalException {

		return _backgroundTaskManager.addBackgroundTask(
			userId, CompanyConstants.SYSTEM,
			StringBundler.concat(
				DLOpenerOneDriveManager.class.getSimpleName(), StringPool.POUND,
				fileEntry.getFileEntryId()),
			UploadOneDriveDocumentBackgroundTaskExecutor.class.getName(),
			HashMapBuilder.<String, Serializable>create(
				3
			).put(
				BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS, true
			).put(
				OneDriveBackgroundTaskConstants.FILE_ENTRY_ID,
				fileEntry.getFileEntryId()
			).put(
				OneDriveBackgroundTaskConstants.LOCALE, locale
			).put(
				OneDriveBackgroundTaskConstants.USER_ID, userId
			).build(),
			new ServiceContext());
	}

	private AccessToken _getAccessToken(long companyId, long userId)
		throws PortalException {

		Optional<AccessToken> accessTokenOptional =
			_oAuth2Manager.getAccessTokenOptional(companyId, userId);

		return accessTokenOptional.orElseThrow(
			() -> new PrincipalException(
				StringBundler.concat(
					"User ", userId,
					" does not have a valid OneDrive access token")));
	}

	private File _getContentFile(long userId, FileEntry fileEntry)
		throws PortalException {

		try {
			AccessToken accessToken = _getAccessToken(
				fileEntry.getCompanyId(), userId);

			IGraphServiceClient iGraphServiceClientBuilder =
				GraphServiceClient.fromConfig(
					DefaultClientConfig.createWithAuthenticationProvider(
						new IAuthenticationProviderImpl(accessToken)));

			IDriveItemStreamRequest iDriveItemStreamRequest =
				iGraphServiceClientBuilder.me(
				).drive(
				).items(
					_getOneDriveReferenceKey(fileEntry)
				).content(
				).buildRequest();

			try (InputStream inputStream = iDriveItemStreamRequest.get()) {
				return FileUtil.createTempFile(inputStream);
			}
		}
		catch (GraphServiceException graphServiceException) {
			throw GraphServiceExceptionPortalExceptionMapper.map(
				graphServiceException);
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	private String _getOneDriveFileId(FileEntry fileEntry)
		throws PortalException {

		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				getDLOpenerFileEntryReference(
					DLOpenerOneDriveConstants.ONE_DRIVE_REFERENCE_TYPE,
					fileEntry);

		return dlOpenerFileEntryReference.getReferenceKey();
	}

	private String _getOneDriveFileTitle(long userId, FileEntry fileEntry)
		throws PortalException {

		try {
			AccessToken accessToken = _getAccessToken(
				fileEntry.getCompanyId(), userId);

			IGraphServiceClient iGraphServiceClientBuilder =
				GraphServiceClient.fromConfig(
					DefaultClientConfig.createWithAuthenticationProvider(
						new IAuthenticationProviderImpl(accessToken)));

			IDriveItemRequest iDriveItemRequest = iGraphServiceClientBuilder.me(
			).drive(
			).items(
				_getOneDriveReferenceKey(fileEntry)
			).buildRequest();

			DriveItem driveItem = iDriveItemRequest.get();

			return driveItem.name;
		}
		catch (GraphServiceException graphServiceException) {
			throw GraphServiceExceptionPortalExceptionMapper.map(
				graphServiceException);
		}
	}

	private String _getOneDriveReferenceKey(FileEntry fileEntry)
		throws PortalException {

		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				getDLOpenerFileEntryReference(
					DLOpenerOneDriveConstants.ONE_DRIVE_REFERENCE_TYPE,
					fileEntry);

		return dlOpenerFileEntryReference.getReferenceKey();
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

	@Reference
	private OAuth2Manager _oAuth2Manager;

	private static class CachingUnsafeSupplier<T, E extends PortalException>
		implements UnsafeSupplier<T, E> {

		public CachingUnsafeSupplier(UnsafeSupplier<T, E> unsafeSupplier) {
			_unsafeSupplier = unsafeSupplier;
		}

		@Override
		public T get() throws E {
			if (_value != null) {
				return _value;
			}

			_value = _unsafeSupplier.get();

			return _value;
		}

		private final UnsafeSupplier<T, E> _unsafeSupplier;
		private T _value;

	}

}