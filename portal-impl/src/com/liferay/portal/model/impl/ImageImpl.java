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

package com.liferay.portal.model.impl;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageImpl extends ImageBaseImpl {

	@Override
	public byte[] getTextObj() {
		if (_textObj != null) {
			return _textObj;
		}

		long imageId = getImageId();

		try {
			DLFileEntry dlFileEntry = null;

			if (PropsValues.WEB_SERVER_SERVLET_CHECK_IMAGE_GALLERY) {
				dlFileEntry =
					DLFileEntryLocalServiceUtil.fetchFileEntryByAnyImageId(
						imageId);
			}

			InputStream is = null;

			if ((dlFileEntry != null) &&
				(dlFileEntry.getLargeImageId() == imageId)) {

				is = DLStoreUtil.getFileAsStream(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName());
			}
			else {
				is = DLStoreUtil.getFileAsStream(
					_DEFAULT_COMPANY_ID, _DEFAULT_REPOSITORY_ID, getFileName());
			}

			byte[] bytes = FileUtil.getBytes(is);

			_textObj = bytes;
		}
		catch (Exception e) {
			_log.error("Error reading image " + imageId, e);
		}

		return _textObj;
	}

	@Override
	public void setTextObj(byte[] textObj) {
		_textObj = textObj;
	}

	protected String getFileName() {
		return getImageId() + StringPool.PERIOD + getType();
	}

	private static final long _DEFAULT_COMPANY_ID = 0;

	private static final long _DEFAULT_REPOSITORY_ID = 0;

	private static final Log _log = LogFactoryUtil.getLog(ImageImpl.class);

	private byte[] _textObj;

}