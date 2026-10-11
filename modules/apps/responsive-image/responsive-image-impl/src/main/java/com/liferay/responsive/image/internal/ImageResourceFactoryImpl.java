/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal;

import com.liferay.document.library.helper.DLURLHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.responsive.image.ImageResource;
import com.liferay.responsive.image.ImageResourceFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(service = ImageResourceFactory.class)
public class ImageResourceFactoryImpl implements ImageResourceFactory {

	@Override
	public ImageResource fromFileEntry(FileEntry fileEntry)
		throws PortalException {

		return new ImageResourceImpl(
			fileEntry.getMimeType(),
			_dlURLHelper.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
				StringPool.BLANK, false, false));
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return null;
		}

		return serviceContext.getThemeDisplay();
	}

	@Reference
	private DLURLHelper _dlURLHelper;

	private static class ImageResourceImpl implements ImageResource {

		public ImageResourceImpl(String mimeType, String url) {
			_mimeType = mimeType;
			_url = url;
		}

		@Override
		public String getMimeType() {
			return _mimeType;
		}

		@Override
		public String getURL() {
			return _url;
		}

		private final String _mimeType;
		private final String _url;

	}

}