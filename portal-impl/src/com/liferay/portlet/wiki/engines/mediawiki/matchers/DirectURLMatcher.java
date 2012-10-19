/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CallbackMatcher;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.wiki.model.WikiPage;

import java.util.regex.MatchResult;

public class DirectURLMatcher extends CallbackMatcher {

	public DirectURLMatcher(WikiPage page, String attachmentURLPrefix) {
		_attachmentURLPrefix = attachmentURLPrefix;
		_page = page;

		setRegex(_REGEX);
	}

	public String replaceMatches(CharSequence charSequence) {
		return replaceMatches(charSequence, _callBack);
	}

	private static final String _REGEX =
		"<a href=\"[^\"]*?Special:Edit[^\"]*?topic=[^\"]*?\".*?title=\"" +
			"([^\"]*?)\".*?>(.*?)</a>";

	private String _attachmentURLPrefix;

	private Callback _callBack = new Callback() {

		public String foundMatch(MatchResult matchResult) {
			String fileName = matchResult.group(1);
			String title = matchResult.group(2);
			String url = _attachmentURLPrefix + HttpUtil.encodeURL(fileName);

			try {
				String[] attachments = _page.getAttachmentsFiles();

				String link =
					StringPool.SLASH + _page.getAttachmentsDir() +
						StringPool.SLASH + fileName;

				if (!ArrayUtil.contains(attachments, link)) {
					return null;
				}
			}
			catch (Exception e) {
				return null;
			}

			StringBundler sb = new StringBundler(5);

			sb.append("<a href=\"");
			sb.append(url);
			sb.append("\">");
			sb.append(title);
			sb.append("</a>");

			return sb.toString();
		}

	};

	private WikiPage _page;

}