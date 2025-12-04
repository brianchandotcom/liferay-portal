/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.manager;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Locale;

/**
 * @author Alicia García
 */
public interface TranslationManager {

	public void addZipEntry(
			ZipWriter zipWriter, String className, long classPK,
			String exportMimeType, String sourceLanguageId,
			String[] targetLanguageIds, Locale locale)
		throws IOException, PortalException;

	public String getEntryTitle(String className, long classPK, Locale locale);

	public String getXLIFFFileName(
		String className, long classPK, String sourceLanguageId,
		String targetLanguageId, Locale locale);

	public InputStream getXLIFFInputStream(
			String className, long classPK, String exportMimeType,
			String sourceLanguageId, String targetLanguageId)
		throws IOException, PortalException;

	public File getXLIFFZipFile(
			String className, long classPK, String exportMimeType,
			Locale locale, String sourceLanguageId, String[] targetLanguageIds,
			User user)
		throws IOException, PortalException;

	public String getZipFileName(
		String className, long classPK, String classNameTitle,
		boolean multipleModels, String sourceLanguageId, Locale locale);

}