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