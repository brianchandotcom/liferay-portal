/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.video.internal.url.provider;

import com.liferay.document.library.url.provider.DLFileVersionURLProvider;
import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.document.library.video.external.shortcut.resolver.DLVideoExternalShortcutResolver;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLFileVersionURLProvider.class)
public class DLVideoExternalShortcutDLFileVersionURLProvider
	implements DLFileVersionURLProvider {

	@Override
	public List<Type> getTypes() {
		return Arrays.asList(Type.IMAGE_PREVIEW, Type.THUMBNAIL);
	}

	@Override
	public String getURL(FileVersion fileVersion, ThemeDisplay themeDisplay) {
		DLVideoExternalShortcut dlVideoExternalShortcut =
			_dlVideoExternalShortcutResolver.resolve(fileVersion);

		if (dlVideoExternalShortcut != null) {
			return dlVideoExternalShortcut.getThumbnailURL();
		}

		return null;
	}

	@Reference
	private DLVideoExternalShortcutResolver _dlVideoExternalShortcutResolver;

}