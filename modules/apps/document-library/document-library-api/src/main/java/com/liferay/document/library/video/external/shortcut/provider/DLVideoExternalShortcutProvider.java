/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.video.external.shortcut.provider;

import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;

/**
 * Provides a way to retrieve a {@link DLVideoExternalShortcut} from a URL.
 *
 * <p>
 * Implement this interface to add support for custom external video providers.
 * The implementation should: <ol> <li> Decide whether or not to process the URL
 * (based on a regex for example). </li> <li> Return <code>null</code> if the
 * URL does not match to allow matching other providers. </li> <li> Fetch extra
 * information from the external service and return a {@link
 * DLVideoExternalShortcut} with the video details. </li> </ol>
 * </p>
 *
 * @author Alejandro Tardín
 * @review
 */
public interface DLVideoExternalShortcutProvider {

	/**
	 * Returns a {@link DLVideoExternalShortcut} from a given URL, or
	 * <code>null</code> if the URL is not recognized.
	 *
	 * @return the DLVideoExternalShortcut or <code>null</code>
	 * @review
	 */
	public DLVideoExternalShortcut getDLVideoExternalShortcut(String url);

}