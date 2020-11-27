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

package com.liferay.document.library.preview.video.internal;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.document.library.preview.DLPreviewRenderer;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.preview.video.internal.constants.DLPreviewVideoWebKeys;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Set;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLPreviewRendererProvider.class)
public class VideoDLPreviewRendererProvider
	implements DLPreviewRendererProvider {

	@Override
	public Set<String> getMimeTypes() {
		return _videoProcessor.getVideoMimeTypes();
	}

	@Override
	public DLPreviewRenderer getPreviewDLPreviewRenderer(
		FileVersion fileVersion) {

		if (!_videoProcessor.isVideoSupported(fileVersion)) {
			return null;
		}

		return (request, response) -> {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/preview/view.jsp");

			request.setAttribute(
				DLPreviewVideoWebKeys.VIDEO_IFRAME_URL,
				_getVideoEmbedURL(request, fileVersion));

			requestDispatcher.include(request, response);
		};
	}

	@Override
	public DLPreviewRenderer getThumbnailDLPreviewRenderer(
		FileVersion fileVersion) {

		return null;
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(type=" + DLProcessorConstants.VIDEO_PROCESSOR + ")",
		unbind = "-"
	)
	protected void setDLProcessor(DLProcessor dlProcessor) {
		_videoProcessor = (VideoProcessor)dlProcessor;
	}

	private String _getVideoEmbedURL(
			HttpServletRequest httpServletRequest, FileVersion fileVersion)
		throws PortalException {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		PortletURL getDLExternalVideoFieldsURL =
			requestBackedPortletURLFactory.createRenderURL(
				DLPortletKeys.DOCUMENT_LIBRARY);

		try {
			getDLExternalVideoFieldsURL.setWindowState(
				LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
		}

		getDLExternalVideoFieldsURL.setParameter(
			"mvcRenderCommandName", "/document_library/embed_video");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		getDLExternalVideoFieldsURL.setParameter(
			"url",
			_dlURLHelper.getPreviewURL(
				fileVersion.getFileEntry(), fileVersion, themeDisplay,
				StringPool.BLANK, false, true));

		return getDLExternalVideoFieldsURL.toString();
	}

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.video)"
	)
	private ServletContext _servletContext;

	private VideoProcessor _videoProcessor;

}