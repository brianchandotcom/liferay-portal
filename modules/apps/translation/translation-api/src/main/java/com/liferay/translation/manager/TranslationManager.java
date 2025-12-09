/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.manager;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.File;
import java.io.IOException;

import java.util.Locale;

/**
 * @author Alicia García
 */
public interface TranslationManager {

	public String getEntryTitle(String className, long classPK, Locale locale);

	public File getXLIFFZipFile(
			String className, long[] classPKs, String exportMimeType,
			Locale locale, String sourceLanguageId, String[] targetLanguageIds)
		throws IOException, PortalException;

}