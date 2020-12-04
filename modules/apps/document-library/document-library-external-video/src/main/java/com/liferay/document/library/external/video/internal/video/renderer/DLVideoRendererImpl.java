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

package com.liferay.document.library.external.video.internal.video.renderer;

import com.liferay.document.library.external.video.DLExternalVideo;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoPortletKeys;
import com.liferay.document.library.external.video.resolver.DLExternalVideoResolver;
import com.liferay.document.library.video.renderer.DLVideoRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

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
		FileEntry fileEntry, HttpServletRequest httpServletRequest) {

		DLExternalVideo dlExternalVideo = _dlExternalVideoResolver.resolve(
			fileEntry);

		if (dlExternalVideo != null) {
			return dlExternalVideo.getEmbeddableHTML();
		}

		return StringBundler.concat(
			"<iframe height=\"315\" frameborder=\"0\" src=\"",
			_getEmbedVideoURL(fileEntry, httpServletRequest), "&",
			"\" width=\"560\"></iframe>");
	}

	private String _getEmbedVideoURL(
		FileEntry fileEntry, HttpServletRequest httpServletRequest) {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		PortletURL getEmbedVideoURL =
			requestBackedPortletURLFactory.createRenderURL(
				DLExternalVideoPortletKeys.DL_EXTERNAL_VIDEO);

		try {
			getEmbedVideoURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
		}

		getEmbedVideoURL.setParameter(
			"mvcRenderCommandName",
			"/document_library_external_video/embed_video");
		getEmbedVideoURL.setParameter(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

		return getEmbedVideoURL.toString();
	}

	@Reference
	private DLExternalVideoResolver _dlExternalVideoResolver;

}