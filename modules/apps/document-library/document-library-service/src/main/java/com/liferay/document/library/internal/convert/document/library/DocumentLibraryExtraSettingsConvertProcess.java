/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.convert.document.library;

import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.convert.BaseConvertProcess;
import com.liferay.portal.convert.ConvertProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 */
@Component(service = ConvertProcess.class)
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
			return _dlFileEntryLocalService.hasExtraSettings();
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

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}