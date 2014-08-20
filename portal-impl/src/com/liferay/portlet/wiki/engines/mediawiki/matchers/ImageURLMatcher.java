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

import com.liferay.portal.kernel.util.CallbackMatcher;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.MatchResult;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class ImageURLMatcher extends CallbackMatcher {

	public ImageURLMatcher(String attachmentURLPrefix) {
		_attachmentURLPrefix = attachmentURLPrefix;

		setRegex(_REGEX);
	}

	public String replaceMatches(CharSequence charSequence) {
		return replaceMatches(charSequence, _callBack);
	}

	private static final String _REGEX =
		"<a href=\"[^\"]*?Special:Upload[^\"]*?topic=Image:([^\"]*?)\".*?</a>";

	private final String _attachmentURLPrefix;

	private final Callback _callBack = new Callback() {

		@Override
		public String foundMatch(MatchResult matchResult) {
			String title = StringUtil.replace(
				matchResult.group(1), "%5F", StringPool.UNDERLINE);

			String url = _attachmentURLPrefix + HttpUtil.encodeURL(title);

			StringBundler sb = new StringBundler(5);

			sb.append("<img alt=\"");
			sb.append(title);
			sb.append("\" class=\"wikiimg\" src=\"");
			sb.append(url);
			sb.append("\" />");

			return sb.toString();
		}

	};

}