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
import com.liferay.document.library.opener.onedrive.web.internal.configuration.DLOneDriveCompanyConfiguration;
import com.liferay.document.library.opener.onedrive.web.internal.graph.IAuthenticationProviderImpl;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.AccessToken;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2Manager;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;

import com.microsoft.graph.core.DefaultClientConfig;
import com.microsoft.graph.models.extensions.DriveItem;
import com.microsoft.graph.models.extensions.File;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.models.extensions.User;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IDriveItemCollectionRequest;
import com.microsoft.graph.requests.extensions.IDriveItemRequest;
import com.microsoft.graph.requests.extensions.IDriveItemStreamRequest;
import com.microsoft.graph.requests.extensions.IUserRequest;

import java.io.InputStream;

import java.util.Optional;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = DLOpenerOneDriveManager.class)
public class DLOpenerOneDriveManager {

	public DLOpenerOneDriveFileReference createFile(
			long userId, FileEntry fileEntry)
		throws PortalException {

		AccessToken accessToken = _getAccessToken(
			fileEntry.getCompanyId(), userId);

		IGraphServiceClient iGraphServiceClientBuilder =
			GraphServiceClient.fromConfig(
				DefaultClientConfig.createWithAuthenticationProvider(
					new IAuthenticationProviderImpl(accessToken)));

		IDriveItemCollectionRequest iDriveItemCollectionRequest =
			iGraphServiceClientBuilder.me(
			).drive(
			).root(
			).children(
			).buildRequest();

		DriveItem driveItem = new DriveItem();

		driveItem.name = fileEntry.getFileName();

		File file = new File();

		file.mimeType = fileEntry.getMimeType();

		driveItem.file = file;

		DriveItem postedDriveItem = iDriveItemCollectionRequest.post(driveItem);

		_dlOpenerFileEntryReferenceLocalService.
			addPlaceholderDLOpenerFileEntryReference(
				userId, fileEntry,
				DLOpenerFileEntryReferenceConstants.TYPE_EDIT);

		_dlOpenerFileEntryReferenceLocalService.
			updateDLOpenerFileEntryReference(postedDriveItem.id, fileEntry);

		return new DLOpenerOneDriveFileReference(
			fileEntry.getFileEntryId(),
			new CachingSupplier<>(
				() -> _getOneDriveFileTitle(userId, fileEntry)),
			() -> _getContentFile(userId, fileEntry),
			() -> _getOneDriveFileURL(userId, fileEntry));
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

		iDriveItemRequest.delete();

		_dlOpenerFileEntryReferenceLocalService.
			deleteDLOpenerFileEntryReference(fileEntry);
	}

	public DLOpenerOneDriveFileReference getDLOpenerOneDriveFileReference(
			long userId, FileEntry fileEntry)
		throws PortalException {

		String googleDriveFileId = _getOneDriveFileId(fileEntry);

		if (Validator.isNull(googleDriveFileId)) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"File entry ", fileEntry.getFileEntryId(),
					" is not a OneDrive file"));
		}

		_getAccessToken(fileEntry.getCompanyId(), userId);

		return new DLOpenerOneDriveFileReference(
			fileEntry.getFileEntryId(),
			new CachingSupplier<>(
				() -> _getOneDriveFileTitle(userId, fileEntry)),
			() -> _getContentFile(userId, fileEntry),
			() -> _getOneDriveFileURL(userId, fileEntry));
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
				fetchDLOpenerFileEntryReference(fileEntry);

		if (dlOpenerFileEntryReference != null) {
			return true;
		}

		return false;
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

	private java.io.File _getContentFile(long userId, FileEntry fileEntry) {
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

			try (InputStream is = iDriveItemStreamRequest.get()) {
				return FileUtil.createTempFile(is);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String _getOneDriveFileId(FileEntry fileEntry)
		throws PortalException {

		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				getDLOpenerFileEntryReference(fileEntry);

		return dlOpenerFileEntryReference.getReferenceKey();
	}

	private String _getOneDriveFileTitle(long userId, FileEntry fileEntry) {
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
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String _getOneDriveFileURL(long userId, FileEntry fileEntry) {
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

			return driveItem.webUrl;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String _getOneDriveReferenceKey(FileEntry fileEntry) {
		try {
			DLOpenerFileEntryReference dlOpenerFileEntryReference =
				_dlOpenerFileEntryReferenceLocalService.
					getDLOpenerFileEntryReference(fileEntry);

			return dlOpenerFileEntryReference.getReferenceKey();
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

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