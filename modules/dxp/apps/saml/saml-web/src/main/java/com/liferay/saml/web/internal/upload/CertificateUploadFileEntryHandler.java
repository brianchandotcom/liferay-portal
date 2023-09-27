/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.upload;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.web.internal.util.SamlTempFileEntryUtil;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.IOException;
import java.io.InputStream;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(service = CertificateUploadFileEntryHandler.class)
public class CertificateUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin()) {
			throw new PrincipalException();
		}

		FileEntry fileEntry = null;

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"file")) {

			fileEntry = SamlTempFileEntryUtil.addTempFileEntry(
				permissionChecker.getUser(),
				uploadPortletRequest.getFileName("file"), inputStream,
				uploadPortletRequest.getContentType("file"));
		}
		catch (PortalException portalException) {
			throw new IOException(portalException);
		}

		try {
			_validateFile(fileEntry);

			return fileEntry;
		}
		catch (Exception exception) {
			TempFileEntryUtil.deleteTempFileEntry(fileEntry.getFileEntryId());

			if (exception instanceof RuntimeException) {
				throw (RuntimeException)exception;
			}

			throw new PortalException(exception);
		}
	}

	private void _validateFile(FileEntry fileEntry)
		throws CertificateException, KeyStoreException {

		try (InputStream inputStream = fileEntry.getContentStream()) {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");

			keyStore.load(inputStream, null);
		}
		catch (IOException ioException) {
			throw new KeyStoreException(ioException);
		}
		catch (KeyStoreException | NoSuchAlgorithmException | PortalException
					exception) {

			throw new SystemException(exception);
		}
	}

}