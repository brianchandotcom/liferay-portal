/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.manager;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alicia García
 * @author Roberto Díaz
 */
public interface TranslationManager {

	public String getTitle(String className, long classPK, Locale locale);

	public File getXLIFFFile(
			String className, long classPK, String xliffMimeType, Locale locale,
			String sourceLanguageId, String targetLanguageId)
		throws IOException, PortalException;

	public File getXLIFFZipFile(
			String className, long[] classPKs, String xliffMimeType,
			Locale locale, String sourceLanguageId, String[] targetLanguageIds)
		throws IOException, PortalException;

	public void processXLIFFTranslation(
			long groupId, String className, long classPK,
			Translation translation, List<String> successMessages,
			List<Map<String, String>> failureMessages, Locale locale,
			ServiceContext serviceContext)
		throws IOException, PortalException;

}