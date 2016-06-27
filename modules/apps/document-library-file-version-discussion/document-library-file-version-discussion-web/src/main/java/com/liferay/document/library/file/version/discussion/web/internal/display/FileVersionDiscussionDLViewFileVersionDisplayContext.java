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

package com.liferay.document.library.file.version.discussion.web.internal.display;

import com.liferay.document.library.display.context.BaseDLViewFileVersionDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class FileVersionDiscussionDLViewFileVersionDisplayContext
	extends BaseDLViewFileVersionDisplayContext {

	public FileVersionDiscussionDLViewFileVersionDisplayContext(
		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext,
		HttpServletRequest request, HttpServletResponse response,
		FileVersion fileVersion, ResourceBundleLoader resourceBundleLoader) {

		super(
			_UUID, dlViewFileVersionDisplayContext, request, response,
			fileVersion);

		_resourceBundleLoader = resourceBundleLoader;
	}

	@Override
	public String getDiscussionClassName() {
		return DLFileVersion.class.getName();
	}

	@Override
	public long getDiscussionClassPK() {
		return fileVersion.getFileVersionId();
	}

	@Override
	public String getDiscussionLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				LanguageUtil.getLanguageId(locale));

		String label = "comments-for-version-x";

		String version = fileVersion.getVersion();

		if (version.equals(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {
			label = "comments-for-draft";
		}

		return LanguageUtil.format(
			resourceBundle, label, fileVersion.getVersion());
	}

	private static final UUID _UUID = UUID.fromString(
		"3D93D2E1-4C75-45FC-804C-4BE7E63561C8");

	private final ResourceBundleLoader _resourceBundleLoader;

}