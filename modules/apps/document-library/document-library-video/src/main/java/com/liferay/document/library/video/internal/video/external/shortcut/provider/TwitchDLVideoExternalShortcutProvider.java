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

package com.liferay.document.library.video.internal.video.external.shortcut.provider;

import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.document.library.video.external.shortcut.provider.DLVideoExternalShortcutProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLVideoExternalShortcutProvider.class)
public class TwitchDLVideoExternalShortcutProvider
	implements DLVideoExternalShortcutProvider {

	@Override
	public DLVideoExternalShortcut getDLVideoExternalShortcut(String url) {
		String twitchVideoId = _getTwitchVideoId(url);

		if (Validator.isNull(twitchVideoId)) {
			return null;
		}

		return new DLVideoExternalShortcut() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public String getThumbnailURL() {
				return null;
			}

			@Override
			public String getTitle() {
				return null;
			}

			@Override
			public String getURL() {
				return url;
			}

			@Override
			public String renderHTML(HttpServletRequest httpServletRequest) {
				return StringUtil.replace(
					_getTpl(_portal.getHost(httpServletRequest)), "{embedId}",
					twitchVideoId);
			}

		};
	}

	private String _getTpl(String host) {
		return StringBundler.concat(
			"<iframe allowfullscreen=\"true\" frameborder=\"0\" ",
			"height=\"315\" ",
			"src=\"https://player.twitch.tv/?autoplay=false&video={embedId}",
			"&parent=", host, "\" scrolling=\"no\" width=\"560\" ></iframe>");
	}

	private String _getTwitchVideoId(String url) {
		Matcher matcher = _urlPattern.matcher(url);

		if (matcher.matches()) {
			return matcher.group(1);
		}

		return null;
	}

	private static final Pattern _urlPattern = Pattern.compile(
		"https?:\\/\\/(?:www\\.)?twitch\\.tv\\/videos\\/(\\S*)$");

	@Reference
	private Portal _portal;

}