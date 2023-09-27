/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.onedrive.web.internal.constants;

/**
 * Provides a set of constants used to initialize a background task to upload
 * content to OneDrive.
 *
 * @author Cristina González
 * @review
 */
public class OneDriveBackgroundTaskConstants {

	/**
	 * Provides the ID of the file entry to link to the OneDrive file.
	 *
	 * @review
	 */
	public static final String FILE_ENTRY_ID = "fileEntryId";

	/**
	 * Provides the ID of the locale used in this background task.
	 *
	 * @review
	 */
	public static final String LOCALE = "locale";

	/**
	 * Indicates the phase of the upload process. There are two possible values:
	 *
	 * <ul>
	 * <li>
	 * {@link #PORTAL_START}: The background task has begun the upload.
	 * </li>
	 * <li>
	 * {@link #PORTAL_END}: The background task has finished the upload.
	 * </li>
	 * </ul>
	 *
	 * @review
	 */
	public static final String PHASE = "phase";

	/**
	 * Defines the {@link #PHASE} attribute's value indicating the end of the
	 * background task's upload process.
	 *
	 * @review
	 */
	public static final String PORTAL_END = "portalEnd";

	/**
	 * Defines the {@link #PHASE} attribute's value indicating the start of the
	 * background task's upload process.
	 *
	 * @review
	 */
	public static final String PORTAL_START = "portalStart";

	/**
	 * Defines the {@link #PHASE} attribute's value indicating the background
	 * task's upload process is in progress.
	 *
	 * @review
	 */
	public static final String PROGRESS = "progress";

	/**
	 * Provides the ID of the user requesting the operation on OneDrive.
	 *
	 * @review
	 */
	public static final String USER_ID = "userId";

}