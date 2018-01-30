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

package com.liferay.document.library.document.conversion.internal;

import com.liferay.portal.kernel.io.AutoDeleteFileInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.InputStream;

import java.util.Scanner;

/**
 * @author Daniel Sanz
 * @author Istvan Andras Dezsi
 */
public class DocumentHTMLProcessor {

	public InputStream process(InputStream inputStream) {
		File tempFile = null;

		InputStream processedInputStream = null;

		Scanner scanner = null;

		String replacement = "";

		try {
			scanner = new Scanner(inputStream);

			scanner.useDelimiter(">");

			tempFile = FileUtil.createTempFile();

			long userId = PrincipalThreadLocal.getUserId();

			String imageRequestToken = ImageRequestTokenUtil.createToken(
				userId);

			while (scanner.hasNext()) {
				String token = scanner.next();

				if (Validator.isNotNull(token)) {
					token += ">";

					replacement = token.replaceAll(
						_DOCUMENTS_REGEX,
						"$1&auth_token=" + imageRequestToken + "$3");

					replacement = replacement.replaceAll(
						_IMAGE_REGEX,
						"$1&auth_token=" + imageRequestToken + "$3");

					replacement = replacement.replaceAll(
						_PORTLET_FILE_ENTRY_REGEX,
						"$1?auth_token=" + imageRequestToken + "$3");

					replacement = replacement.replaceAll(
						_WIKI_PAGE_ATTACHMENT_REGEX,
						"$1$3&auth_token=" + imageRequestToken + "$6");

					FileUtil.write(tempFile, replacement, true, true);
				}
			}

			processedInputStream = new AutoDeleteFileInputStream(tempFile);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			scanner.close();
		}

		return processedInputStream;
	}

	private static final String _DOCUMENTS_REGEX =
		"(<img .*?src=\"\\/(documents\\/\\d+)\\/[^\\s]+)(\"[^&]+)";

	private static final String _IMAGE_REGEX =
		"(<img .*?src=\"\\/(image)\\/[^\\s]+)(\"[^&]+)";

	private static final String _PORTLET_FILE_ENTRY_REGEX =
		"(<img .*?src=\"\\/(documents\\/portlet_file_entry)\\/[^&]+)(\"[^&]+)";

	private static final String _WIKI_PAGE_ATTACHMENT_REGEX =
		"(<img ([^=]*(?<!src)=\\\"[^\\\"]+\\\")*[^=]*)((?<= src)=\\\"[^\\/]*(" +
			"\\/(?!c/wiki/get_page_attachment)[^\\/]*)*(\\/(?=" +
				"c/wiki/get_page_attachment))[^&]+)([^>]+>)";

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentHTMLProcessor.class);

}