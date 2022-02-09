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

package com.liferay.portal.convert.documentlibrary;

import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.convert.BaseConvertProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Alexander Chow
 */
public class DocumentLibraryExtraSettingsConvertProcess
	extends BaseConvertProcess {

	@Override
	public String getDescription() {
		return "convert-extra-settings-from-documents-and-media-files";
	}

	@Override
	public boolean hasCustomView() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		try {
			return DLFileEntryLocalServiceUtil.hasExtraSettings();
		}
		catch (Exception exception) {
			_log.error(exception);

			return false;
		}
	}

	@Override
	protected void doConvert() {
	}

	@Override
	protected String getJspPath() {
		return "/edit_document_library_extra_settings.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentLibraryExtraSettingsConvertProcess.class);

}