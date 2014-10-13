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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.MatchResult;

/**
 * @author Shinn Lok
 */
public class ImageTagMatcher extends CallbackMatcher {

	public ImageTagMatcher() {
		setRegex(_REGEX);
	}

	public String replaceMatches(CharSequence charSequence) {
		return replaceMatches(charSequence, _callBack);
	}

	private static final String _REGEX = "\\[\\[Image:[^\\]]+\\]\\]";

	private final Callback _callBack = new Callback() {

		@Override
		public String foundMatch(MatchResult matchResult) {
			String title = matchResult.group(0);

			title = StringUtil.replace(title, StringPool.UNDERLINE, "%5F");

			return title;
		}

	};

}