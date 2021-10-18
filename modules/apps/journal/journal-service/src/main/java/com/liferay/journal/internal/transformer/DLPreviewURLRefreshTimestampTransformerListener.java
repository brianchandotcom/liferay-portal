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

package com.liferay.journal.internal.transformer;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.templateparser.BaseTransformerListener;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author István András Dézsi
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
	service = TransformerListener.class
)
public class DLPreviewURLRefreshTimestampTransformerListener
	extends BaseTransformerListener {

	@Override
	public String onOutput(
		String output, String languageId, Map<String, String> tokens) {

		if (_log.isDebugEnabled()) {
			_log.debug("onOutput");
		}

		return _replace(output);
	}

	private FileEntry _getFileEntry(Matcher matcher) throws PortalException {
		if (matcher.group(5) != null) {
			long groupId = Long.valueOf(matcher.group(2));

			String uuid = matcher.group(5);

			return _dlAppLocalService.getFileEntryByUuidAndGroupId(
				uuid, groupId);
		}

		long groupId = Long.valueOf(matcher.group(2));
		long folderId = Long.valueOf(matcher.group(3));
		String title = matcher.group(4);

		try {
			return _dlAppLocalService.getFileEntry(groupId, folderId, title);
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFileEntryException, noSuchFileEntryException);
			}

			return _dlAppLocalService.getFileEntryByFileName(
				groupId, folderId, title);
		}
	}

	private String _getReplacement(Matcher matcher) throws PortalException {
		FileEntry fileEntry = _getFileEntry(matcher);

		if (fileEntry == null) {
			return matcher.group(0);
		}

		Date modifiedDate = fileEntry.getModifiedDate();

		long timeStamp = modifiedDate.getTime();

		StringBundler sb = new StringBundler(4);

		sb.append(matcher.group(1));
		sb.append("?t=");
		sb.append(timeStamp);
		sb.append(matcher.group(6));

		return sb.toString();
	}

	private String _replace(String s) {
		if (Validator.isNull(s) || !s.contains("/documents/")) {
			return s;
		}

		Matcher matcher = _pattern.matcher(s);

		try {
			StringBuffer sb = null;

			while (matcher.find()) {
				if (sb == null) {
					sb = new StringBuffer(s.length());
				}

				String replacement = _getReplacement(matcher);

				matcher.appendReplacement(
					sb, Matcher.quoteReplacement(replacement));
			}

			if (sb != null) {
				matcher.appendTail(sb);

				return sb.toString();
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException.getMessage());
			}
		}

		return s;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLPreviewURLRefreshTimestampTransformerListener.class);

	private static final Pattern _pattern = Pattern.compile(
		"(<(?:a|img)\\s+(?:[^>]*\\s)*(?:href|src)=['\"](?:/?[^\\s]*)" +
			"/documents/(\\d+)/(\\d+)/([^/?]+)(?:/([-0-9a-fA-F]+))?)" +
				"(?:\\?t=\\d+)?(['\"](?:\\s*>.*</a>|[^>]*/>))");

	@Reference
	private DLAppLocalService _dlAppLocalService;

}