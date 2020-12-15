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
		String videoQueryParam = _getVideoQueryParam(url);

		if (videoQueryParam == null) {
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
				return StringBundler.concat(
					"<iframe allowfullscreen=\"true\" frameborder=\"0\" ",
					"height=\"315\" ",
					"src=\"https://player.twitch.tv/?autoplay=false&",
					videoQueryParam, "&parent=",
					_portal.getHost(httpServletRequest),
					"\" scrolling=\"no\" width=\"560\" ></iframe>");
			}

		};
	}

	private String _getTwitchVideoId(Pattern pattern, String url) {
		Matcher matcher = pattern.matcher(url);

		if (matcher.matches()) {
			return matcher.group(1);
		}

		return null;
	}

	private String _getVideoQueryParam(String url) {
		String twitchVideoId = _getTwitchVideoId(_videoURLPattern, url);

		if (Validator.isNull(twitchVideoId)) {
			twitchVideoId = _getTwitchVideoId(_channelURLPattern, url);

			if (Validator.isNull(twitchVideoId)) {
				return null;
			}

			return "channel=" + twitchVideoId;
		}

		return "video=" + twitchVideoId;
	}

	private static final Pattern _channelURLPattern = Pattern.compile(
		"https?:\\/\\/(?:www\\.)?twitch\\.tv\\/(\\S*)$");
	private static final Pattern _videoURLPattern = Pattern.compile(
		"https?:\\/\\/(?:www\\.)?twitch\\.tv\\/videos\\/(\\S*)$");

	@Reference
	private Portal _portal;

}