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

package com.liferay.document.library.external.video.internal.provider;

import com.liferay.document.library.external.video.internal.ExternalVideo;
import com.liferay.petra.string.StringBundler;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = ExternalVideoProvider.class)
public class FacebookExternalVideoProvider implements ExternalVideoProvider {

	@Override
	public ExternalVideo getExternalVideo(String url) {
		if (!_matches(url)) {
			return null;
		}

		return new ExternalVideo() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public String getEmbeddableHTML() {
				return StringBundler.concat(
					"<iframe allowFullScreen=\"true\" ",
					"allowTransparency=\"true\" frameborder=\"0\" ",
					"height=\"315\" ",
					"src=\"https://www.facebook.com/plugins/video.php?href=",
					url,
					"&show_text=0&width=560&height=315\" scrolling=\"no\" ",
					"style=\"border:none;overflow:hidden\" width=\"560\">",
					"</iframe>");
			}

			@Override
			public String getTitle() {
				return null;
			}

			@Override
			public String getURL() {
				return url;
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
		Pattern.compile("(https?://(?:www\\.)?facebook.com/watch/?v=\\S*)"),
		Pattern.compile("(https?://(?:www\\.)?facebook.com/\\S*/videos/\\S*)"),
		Pattern.compile("(https?://(?:www.)?facebook.com/watch/?\\?v=.*)"));

}