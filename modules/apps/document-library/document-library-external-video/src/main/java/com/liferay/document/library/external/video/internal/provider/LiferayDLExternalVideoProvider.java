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
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoConstants;
import com.liferay.document.library.external.video.provider.DLExternalVideoProvider;
import com.liferay.frontend.editor.embed.EditorEmbedProvider;
import com.liferay.frontend.editor.embed.constants.EditorEmbedProviderTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "type=" + EditorEmbedProviderTypeConstants.VIDEO,
	service = {DLExternalVideoProvider.class, EditorEmbedProvider.class}
)
public class LiferayDLExternalVideoProvider
	implements DLExternalVideoProvider, EditorEmbedProvider {

	@Override
	public DLExternalVideo getDLExternalVideo(String url) {
		Matcher matcher = _urlPattern.matcher(url);

		if (!matcher.matches()) {
			return null;
		}

		return new DLExternalVideo() {

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public String getEmbeddableHTML() {
				return StringUtil.replace(
					getTpl(), "{embedId}", matcher.group(1));
			}

			@Override
			public String getIconURL() {
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

		};
	}

	@Override
	public String getId() {
		return "liferay";
	}

	@Override
	public String getTpl() {
		return StringBundler.concat(
			"<iframe height=\"315\" frameborder=\"0\" src=\"",
			Portal.PATH_MODULE, "/", DLExternalVideoConstants.SERVLET_PATH,
			"?url={embedId}\" width=\"560\"></iframe>");
	}

	@Override
	public String[] getURLSchemes() {
		return new String[] {_urlPattern.pattern()};
	}

	private static final Pattern _urlPattern = Pattern.compile(
		"(.*\\/documents\\/.*)");

}