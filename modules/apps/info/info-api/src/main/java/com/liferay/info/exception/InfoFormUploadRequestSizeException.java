/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.exception;

import com.liferay.portal.kernel.exception.InfoFormException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upload.configuration.UploadServletRequestConfigurationProviderUtil;

import java.util.Locale;

/**
 * @author Víctor Galán
 */
public class InfoFormUploadRequestSizeException extends InfoFormException {

	@Override
	public String getLocalizedMessage(Locale locale) {
		return LanguageUtil.format(
			locale,
			"file-size-is-larger-than-the-allowed-overall-maximum-upload-" +
				"request-size-x-mb",
			UploadServletRequestConfigurationProviderUtil.getMaxSize() /
				(1024 * 1024),
			false);
	}

}