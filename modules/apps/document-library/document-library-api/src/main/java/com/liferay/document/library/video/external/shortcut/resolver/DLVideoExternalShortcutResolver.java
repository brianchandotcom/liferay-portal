/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.video.external.shortcut.resolver;

import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alejandro Tardín
 */
@ProviderType
public interface DLVideoExternalShortcutResolver {

	public DLVideoExternalShortcut resolve(FileVersion fileVersion);

	public DLVideoExternalShortcut resolve(String url);

}