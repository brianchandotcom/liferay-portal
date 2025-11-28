/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.attachment;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.exportimport.attachment.ExportImportAttachmentManager;
import com.liferay.exportimport.internal.lar.PortletDataContextThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ExportImportAttachmentManager.class)
public class ExportImportAttachmentManagerImpl
	implements ExportImportAttachmentManager {

	@Override
	public String getFileURL(DLFileEntry dlFileEntry) throws Exception {
		PortletDataContext portletDataContext =
			PortletDataContextThreadLocal.getPortletDataContext();

		if ((portletDataContext == null) ||
			(portletDataContext.getZipWriter() == null)) {

			FileEntry fileEntry = _dlAppLocalService.getFileEntry(
				dlFileEntry.getFileEntryId());

			Company company = _companyLocalService.getCompany(
				fileEntry.getCompanyId());

			boolean secure = _isSecure();

			String baseURL = _portal.getPortalURL(
				company.getVirtualHostname(),
				_portal.getPortalServerPort(secure), secure);

			return baseURL + _dlurlHelper.getThumbnailSrc(fileEntry, null);
		}

		try (InputStream inputStream = dlFileEntry.getContentStream()) {
			String fileKey = String.valueOf(dlFileEntry.getFileEntryId());

			portletDataContext.addZipEntry(_getZipPath(fileKey), inputStream);

			return _PROTOCOL + ":" + fileKey;
		}
	}

	@Override
	public URL getURL(String url) throws MalformedURLException {
		PortletDataContext portletDataContext =
			PortletDataContextThreadLocal.getPortletDataContext();

		if (!url.startsWith(_PROTOCOL + ":") || (portletDataContext == null)) {
			return new URL(url);
		}

		return new URL(
			null, url,
			new URLStreamHandler() {

				protected URLConnection openConnection(URL url) {
					return new URLConnection(url) {

						public void connect() {
						}

						@Override
						public InputStream getInputStream() {
							return portletDataContext.getZipEntryAsInputStream(
								_getZipPath(url.getPath()));
						}

					};
				}

			});
	}

	private String _getZipPath(String key) {
		return "batch-binaries/" + key;
	}

	private boolean _isSecure() {
		if (Objects.equals(
				Http.HTTPS,
				PropsUtil.get(PropsKeys.PORTAL_INSTANCE_PROTOCOL)) ||
			Objects.equals(
				Http.HTTPS, PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL))) {

			return true;
		}

		return false;
	}

	private static final String _PROTOCOL = "lar";

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private Portal _portal;

}