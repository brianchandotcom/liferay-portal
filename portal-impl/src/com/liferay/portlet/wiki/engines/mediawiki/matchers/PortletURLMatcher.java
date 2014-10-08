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

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.CallbackMatcher;
import com.liferay.portal.kernel.util.CharPool;

import java.util.regex.MatchResult;

import javax.portlet.PortletURL;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class PortletURLMatcher extends CallbackMatcher {

	public PortletURLMatcher(PortletURL portletURL) {
		_portletURL = portletURL;

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		liferayPortletURL.setParameter("title", _TITLE_PLACEHOLDER, false);
	}

	public String replaceMatches(CharSequence charSequence) {
		return replaceMatches(charSequence, _callBack);
	}

	private static final String _TITLE_PLACEHOLDER = "__TITLE_PLACEHOLDER__";

	private final Callback _callBack = new Callback() {

		@Override
		public String foundMatch(MatchResult matchResult) {
			String portletURLString = _portletURL.toString();

			String title = matchResult.group(1);

			title = title.replace(CharPool.UNDERLINE, CharPool.PLUS);

			String url = portletURLString.replace(_TITLE_PLACEHOLDER, title);

			return "<a href=\"" + url + "\"";
		}

	};

	private final PortletURL _portletURL;

}