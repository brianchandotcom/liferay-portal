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
import com.liferay.petra.string.StringPool;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLVideoExternalShortcutProvider.class)
public class FacebookDLVideoExternalShortcutProvider
	implements DLVideoExternalShortcutProvider {

	@Override
	public DLVideoExternalShortcut getDLVideoExternalShortcut(String url) {
		if (!_matches(url)) {
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
				try {
					return StringBundler.concat(
						"<iframe allowFullScreen=\"true\" allowTransparency=",
						"\"true\" frameborder=\"0\" height=\"315\" ",
						"src=\"https://www.facebook.com/plugins/video.php?",
						"height=315&href=",
						URLEncoder.encode(url, StringPool.UTF8),
						"&show_text=0&width=560\" scrolling=\"no\" ",
						"style=\"border: none; overflow: hidden;\" ",
						"width=\"560\"></iframe>");
				}
				catch (UnsupportedEncodingException
							unsupportedEncodingException) {

					return null;
				}
			}

		};
	}

	private boolean _matches(String url) {
		for (Pattern urlPattern : _urlPatterns) {
			Matcher matcher = urlPattern.matcher(url);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	private static final List<Pattern> _urlPatterns = Arrays.asList(
		Pattern.compile(
			"(https?:\\/\\/(?:(www|m)\\.)?facebook\\.com\\/watch\\/?\\?" +
				"v=\\S*)"),
		Pattern.compile(
			"(https?:\\/\\/(?:www\\.)?facebook\\.com\\/\\S*\\/videos\\/\\S*)"),
		Pattern.compile("(https?:\\/\\/fb\\.watch\\/\\S*)"));

}