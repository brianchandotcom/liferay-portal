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

import com.liferay.document.library.external.video.DLExternalVideo;
import com.liferay.document.library.external.video.provider.DLExternalVideoProvider;
import com.liferay.frontend.editor.embed.EditorEmbedProvider;
import com.liferay.frontend.editor.embed.constants.EditorEmbedProviderTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "type=" + EditorEmbedProviderTypeConstants.VIDEO,
	service = {DLExternalVideoProvider.class, EditorEmbedProvider.class}
)
public class TwitchDLExternalVideoProvider
	implements DLExternalVideoProvider, EditorEmbedProvider {

	@Override
	public DLExternalVideo getDLExternalVideo(String url) {
		String twitchVideoId = _getTwitchVideoId(url);

		if (Validator.isNull(twitchVideoId)) {
			return null;
		}

		return new DLExternalVideo() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public String getEmbeddableHTML() {
				return StringUtil.replace(getTpl(), "{embedId}", twitchVideoId);
			}

			@Override
			public String getIconURL() {
				return _servletContext.getContextPath() + "/icons/twitch.png";
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

	@Override
	public String getId() {
		return "twitch";
	}

	@Override
	public String getTpl() {
		return StringBundler.concat(
			"<iframe allowfullscreen=\"true\" frameborder=\"0\" ",
			"height=\"315\" ",
			"src=\"https://player.twitch.tv/?autoplay=false&video={embedId}",
			"&parent=", _getHost(),
			"\" scrolling=\"no\" width=\"560\" ></iframe>");
	}

	@Override
	public String[] getURLSchemes() {
		return new String[] {_urlPattern.pattern()};
	}

	private String _getHost() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			HttpServletRequest httpServletRequest = serviceContext.getRequest();

			if (httpServletRequest != null) {
				return _portal.getHost(httpServletRequest);
			}
		}

		return StringPool.BLANK;
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

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.external.video)"
	)
	private ServletContext _servletContext;

}