/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.admin.web.internal.util;

import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Andrea Sbarra
 */
public class CSDiagramSettingUtil {

	public static String getImageURL(
			CSDiagramSetting csDiagramSetting, DLURLHelper dlURLHelper)
		throws Exception {

		FileEntry fileEntry = _fetchFileEntry(csDiagramSetting);

		if (fileEntry != null) {
			return dlURLHelper.getDownloadURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);
		}

		return StringPool.BLANK;
	}

	private static FileEntry _fetchFileEntry(CSDiagramSetting csDiagramSetting)
		throws Exception {

		try {
			CPAttachmentFileEntry cpAttachmentFileEntry =
				csDiagramSetting.getCPAttachmentFileEntry();

			return cpAttachmentFileEntry.fetchFileEntry();
		}
		catch (NoSuchCPAttachmentFileEntryException
					noSuchCPAttachmentFileEntryException) {

			if (_log.isInfoEnabled()) {
				_log.info(
					noSuchCPAttachmentFileEntryException,
					noSuchCPAttachmentFileEntryException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CSDiagramSettingUtil.class);

}