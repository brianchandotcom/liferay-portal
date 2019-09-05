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

package com.liferay.html.preview.model.impl;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Pavel Savinov
 */
public class HtmlPreviewEntryImpl extends HtmlPreviewEntryBaseImpl {

	@Override
	public String getImagePreviewURL(ThemeDisplay themeDisplay) {
		long fileEntryId = getFileEntryId();

		if (fileEntryId <= 0) {
			return StringPool.BLANK;
		}

		try {
			return DLUtil.getImagePreviewURL(
				DLAppLocalServiceUtil.getFileEntry(fileEntryId), themeDisplay);
		}
		catch (Exception e) {
			_log.error("Unable to get HTML preview entry image URL", e);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HtmlPreviewEntryImpl.class);

}