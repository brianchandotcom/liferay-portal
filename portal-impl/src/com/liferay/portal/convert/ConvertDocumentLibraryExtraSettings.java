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

package com.liferay.portal.convert;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

/**
 * @author Alexander Chow
 */
public class ConvertDocumentLibraryExtraSettings extends BaseConvertProcess {

	@Override
	public String getDescription() {
		return "convert-extra-settings-from-document-library-files";
	}

	@Override
	public String getPath() {
		return "/admin_server/edit_document_library_extra_settings";
	}

	@Override
	public boolean isEnabled() {
		try {
			return DLFileEntryLocalServiceUtil.hasExtraSettings();
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	@Override
	protected void doConvert() throws Exception {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConvertDocumentLibraryExtraSettings.class);

}