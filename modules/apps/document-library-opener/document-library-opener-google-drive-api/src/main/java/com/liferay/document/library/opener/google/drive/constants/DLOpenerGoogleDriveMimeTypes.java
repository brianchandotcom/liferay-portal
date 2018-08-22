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

package com.liferay.document.library.opener.google.drive.constants;

import com.liferay.portal.kernel.util.ContentTypes;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public class DLOpenerGoogleDriveMimeTypes {

	public static final String APPLICATION_VND_DOCX =
		"application/vnd.openxmlformats-officedocument.wordprocessingml." +
			"document";

	public static final String APPLICATION_VND_GOOGLE_APPS_DOCUMENT =
		"application/vnd.google-apps.document";

	public static boolean isMimeTypeSupported(String mimeType) {
		return _mimeTypes.contains(mimeType);
	}

	private static final Collection<String> _mimeTypes = Arrays.asList(
		ContentTypes.APPLICATION_TEXT, APPLICATION_VND_DOCX, ContentTypes.TEXT,
		ContentTypes.TEXT_PLAIN);

}