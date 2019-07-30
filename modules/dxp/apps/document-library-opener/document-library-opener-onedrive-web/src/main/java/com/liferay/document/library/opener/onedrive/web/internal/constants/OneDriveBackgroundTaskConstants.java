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
	 * Defines the {@link #CMD} attribute's value. This tells the background
	 * task that it should perform a checkout, copying the Documents and Media
	 * file's contents to a new OneDrive file.
	 *
	 * @review
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
	 *
	 * @review
	 */
	public static final String CMD = "cmd";

	/**
	 * Provides the company ID of the file to upload.
	 *
	 * @review
	 */
	public static final String COMPANY_ID = "companyId";

	/**
	 * Defines the {@link #CMD} attribute's value. This tells the background
	 * task that it should create a new empty OneDrive file.
	 *
	 * @review
	 */
	public static final String CREATE = "create";

	/**
	 * Provides the ID of the file entry to link to the OneDrive file.
	 *
	 * @review
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
	 * Provides the ID of the user requesting the operation on OneDrive.
	 *
	 * @review
	 */
	public static final String USER_ID = "userId";

}