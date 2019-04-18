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

package com.liferay.document.library.opener.google.drive.web.internal.constants;

/**
 * Provides a set of constants used to initialize a background task to upload
 * content to Google Drive.
 *
 * @author Sergio González
 */
public class GoogleDriveBackgroundTaskConstants {

	/**
	 * Defines the {@link #CMD} attribute's value. This tells the background
	 * task that it should perform a checkout, copying the Documents and Media
	 * file's contents to a new Google Drive file.
	 */
	public static final String CHECKOUT = "checkout";

	/**
	 * Indicates to the background task which operation is being performed by
	 * Documents and Media. Possible values are
	 *
	 * <ul>
	 * <li>
	 * {@link #CHECKOUT}
	 * </li>
	 * <li>
	 * {@link #CREATE}
	 * </li>
	 * </ul>
	 */
	public static final String CMD = "cmd";

	/**
	 * Provides the company ID of the file to upload.
	 */
	public static final String COMPANY_ID = "companyId";

	/**
	 * Defines the {@link #CMD} attribute's value. This tells the background
	 * task that it should create a new empty Google Drive file.
	 */
	public static final String CREATE = "create";

	public static final String DOCS_GOOGLE_COM_URL = "https://docs.google.com/";

	/**
	 * Provides the ID of the file entry to link to the Google Drive file.
	 */
	public static final String FILE_ENTRY_ID = "fileEntryId";

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
	 */
	public static final String PHASE = "phase";

	/**
	 * Defines the {@link #PHASE} attribute's value indicating the end of the
	 * background task's upload process.
	 */
	public static final String PORTAL_END = "portalEnd";

	/**
	 * Defines the {@link #PHASE} attribute's value indicating the start of the
	 * background task's upload process.
	 */
	public static final String PORTAL_START = "portalStart";

	public static final String PROGRESS = "progress";

	/**
	 * Provides the ID of the user requesting the operation on Google Drive.
	 */
	public static final String USER_ID = "userId";

}