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