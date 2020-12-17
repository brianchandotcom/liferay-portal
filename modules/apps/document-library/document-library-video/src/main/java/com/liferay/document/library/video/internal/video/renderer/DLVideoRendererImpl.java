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

package com.liferay.document.library.video.internal.video.renderer;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.document.library.video.external.shortcut.resolver.DLVideoExternalShortcutResolver;
import com.liferay.document.library.video.renderer.DLVideoRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLVideoRenderer.class)
public class DLVideoRendererImpl implements DLVideoRenderer {

	@Override
	public String renderHTML(
		FileVersion fileVersion, HttpServletRequest httpServletRequest) {

		DLVideoExternalShortcut dlVideoExternalShortcut =
			_dlVideoExternalShortcutResolver.resolve(fileVersion);

		if (dlVideoExternalShortcut != null) {
			return dlVideoExternalShortcut.renderHTML(httpServletRequest);
		}

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return StringBundler.concat(
				"<iframe data-video-liferay height=\"315\" frameborder=\"0\" ",
				"src=\"",
				_dlURLHelper.getPreviewURL(
					fileVersion.getFileEntry(), fileVersion, themeDisplay,
					"&videoEmbed=true", true, false),
				"\" width=\"560\"></iframe>");
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLVideoRendererImpl.class);

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private DLVideoExternalShortcutResolver _dlVideoExternalShortcutResolver;

}