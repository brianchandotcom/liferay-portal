/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.content.transformer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseRegexStringContentTransformer
	implements ContentTransformer {

	@Override
	public String transform(String content) throws PortalException {
		if (Validator.isNull(content)) {
			return content;
		}

		Pattern pattern = getPattern();

		Matcher matcher = pattern.matcher(content);

		StringBuffer sb = null;

		while (matcher.find()) {
			if (sb == null) {
				sb = new StringBuffer(content.length());
			}

			FileEntry fileEntry = getFileEntry(matcher);

			if (!isSupported(fileEntry)) {
				return content;
			}

			String replacement = getReplacement(matcher.group(0), fileEntry);

			matcher.appendReplacement(
				sb, Matcher.quoteReplacement(replacement));
		}

		if (sb != null) {
			matcher.appendTail(sb);

			return sb.toString();
		}

		return content;
	}

	protected abstract FileEntry getFileEntry(Matcher matcher)
		throws PortalException;

	protected abstract Pattern getPattern();

	protected abstract String getReplacement(
			String originalImgTag, FileEntry fileEntry)
		throws PortalException;

	protected abstract boolean isSupported(FileEntry fileEntry);

}