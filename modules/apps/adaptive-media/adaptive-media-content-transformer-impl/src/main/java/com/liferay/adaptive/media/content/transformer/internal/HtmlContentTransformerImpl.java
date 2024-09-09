/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.content.transformer.internal;

import com.liferay.adaptive.media.content.transformer.ContentTransformer;
import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Alejandro Tardín
 */
public class HtmlContentTransformerImpl implements ContentTransformer {

	public HtmlContentTransformerImpl(
		AMImageHTMLTagFactory amImageHTMLTagFactory,
		AMImageMimeTypeProvider amImageMimeTypeProvider,
		DLAppLocalService dlAppLocalService) {

		_amImageHTMLTagFactory = amImageHTMLTagFactory;
		_amImageMimeTypeProvider = amImageMimeTypeProvider;
		_dlAppLocalService = dlAppLocalService;
	}

	@Override
	public String transform(String html) throws PortalException {
		if (html == null) {
			return null;
		}

		StringBundler sb = new StringBundler();

		int i = 0;

		while (i < html.length()) {
			int imgStart = html.indexOf(_OPEN_TAG_TOKEN_IMG, i);

			if (imgStart == -1) {
				sb.append(html.substring(i));

				break;
			}

			int imgEnd = html.indexOf(
				CharPool.GREATER_THAN, imgStart + _OPEN_TAG_TOKEN_IMG.length());

			if (imgEnd == -1) {
				sb.append(html.substring(i));

				break;
			}

			imgEnd++;

			int attributeStart = html.indexOf(
				AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID, imgStart);

			if (attributeStart == -1) {
				sb.append(html.substring(i));

				break;
			}

			if (attributeStart > imgEnd) {
				sb.append(html.substring(i, imgEnd));

				i = imgEnd;

				continue;
			}

			int fileEntryIdStart =
				attributeStart +
					AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID.length() +
						2;

			int fileEntryIdEnd = html.indexOf(CharPool.QUOTE, fileEntryIdStart);

			if ((fileEntryIdEnd == -1) || (fileEntryIdEnd > imgEnd)) {
				sb.append(html.substring(i, imgEnd));

				i = imgEnd;

				continue;
			}

			long fileEntryId = GetterUtil.getLong(
				html.substring(fileEntryIdStart, fileEntryIdEnd));

			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			if (!_amImageMimeTypeProvider.isMimeTypeSupported(
					fileEntry.getMimeType())) {

				sb.append(html.substring(i, imgEnd));

				i = imgEnd;

				continue;
			}

			String replacement = _amImageHTMLTagFactory.create(
				html.substring(imgStart, imgEnd), fileEntry);

			sb.append(html.substring(i, imgStart));
			sb.append(replacement);

			i = imgEnd;
		}

		return sb.toString();
	}

	private static final String _OPEN_TAG_TOKEN_IMG = "<img ";

	private final AMImageHTMLTagFactory _amImageHTMLTagFactory;
	private final AMImageMimeTypeProvider _amImageMimeTypeProvider;
	private final DLAppLocalService _dlAppLocalService;

}