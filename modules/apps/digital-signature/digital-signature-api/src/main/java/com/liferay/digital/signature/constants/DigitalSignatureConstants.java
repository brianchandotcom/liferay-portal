/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.signature.constants;

/**
 * @author Keven Leone
 */
public class DigitalSignatureConstants {

	public static final String[] ALLOWED_FILE_EXTENSIONS = {
		"csv", "doc", "docm", "docx", "dot", "dotm", "dotx", "gif", "htm",
		"html", "jpeg", "jpg", "msg", "pdf", "png", "pot", "potx", "pps", "ppt",
		"pptm", "pptx", "rtf", "rtf", "tif", "tiff", "txt", "wpd", "xls",
		"xlsm", "xlsx", "xps"
	};

	public static final String[] SITE_SETTINGS_STRATEGIES = {
		"always-inherit", "always-override", "inherit-or-override"
	};

}