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

package com.liferay.portlet.wiki.engines.mediawiki.matchers;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.CallbackMatcher;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.wiki.model.WikiPage;

import java.util.regex.MatchResult;

/**
 * @author Kenneth Chang
 */
public class DirectURLMatcher extends CallbackMatcher {

	public DirectURLMatcher(WikiPage page, String attachmentURLPrefix) {
		_page = page;
		_attachmentURLPrefix = attachmentURLPrefix;

		setRegex(_URL_REGEX);
	}

	public String replaceMatches(CharSequence charSequence) {
		return replaceMatches(charSequence, _callBack);
	}

	private static final String _URL_REGEX =
		"<a href=\"[^\"]*?Special:Edit[^\"]*?topic=[^\"]*?\".*?title=\"" +
			"([^\"]*?)\".*?>(.*?)</a>";

	private final String _attachmentURLPrefix;

	private final Callback _callBack = new Callback() {

		@Override
		public String foundMatch(MatchResult matchResult) {
			String fileName = StringUtil.replace(
				matchResult.group(1), "%5F", StringPool.UNDERLINE);
			String title = StringUtil.replace(
				matchResult.group(2), "%5F", StringPool.UNDERLINE);

			if (Validator.isNull(title)) {
				title = fileName;
			}

			String url = _attachmentURLPrefix + HttpUtil.encodeURL(fileName);

			try {
				for (FileEntry fileEntry : _page.getAttachmentsFileEntries()) {
					if (!fileName.equals(fileEntry.getTitle())) {
						continue;
					}

					StringBundler sb = new StringBundler(5);

					sb.append("<a href=\"");
					sb.append(url);
					sb.append("\">");
					sb.append(title);
					sb.append("</a>");

					return sb.toString();
				}
			}
			catch (Exception e) {
			}

			return null;
		}

	};

	private final WikiPage _page;

}