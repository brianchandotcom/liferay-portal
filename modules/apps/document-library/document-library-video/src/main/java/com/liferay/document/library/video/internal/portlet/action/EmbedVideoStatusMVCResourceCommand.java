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

package com.liferay.document.library.video.internal.portlet.action;

import com.liferay.document.library.constants.DLFileVersionPreviewConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.document.library.video.internal.constants.DLVideoPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + DLVideoPortletKeys.DL_VIDEO,
		"mvc.command.name=/document_library_video/get_embed_video_status"
	},
	service = MVCResourceCommand.class
)
public class EmbedVideoStatusMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	public void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		resourceResponse.setStatus(_getEmbedVideoStatus(resourceRequest));
	}

	private int _getEmbedVideoStatus(ResourceRequest resourceRequest) {
		try {
			FileVersion fileVersion = _dlAppLocalService.getFileVersion(
				ParamUtil.getLong(resourceRequest, "fileVersionId"));

			if (fileVersion != null) {
				if (_isPreviewFailure(fileVersion)) {
					return HttpServletResponse.SC_SERVICE_UNAVAILABLE;
				}
				else if (!_videoProcessor.hasVideo(fileVersion)) {
					return HttpServletResponse.SC_ACCEPTED;
				}
				else {
					return HttpServletResponse.SC_OK;
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return HttpServletResponse.SC_SERVICE_UNAVAILABLE;
	}

	private boolean _isPreviewFailure(FileVersion fileVersion) {
		if (_dlFileVersionPreviewLocalService.hasDLFileVersionPreview(
				fileVersion.getFileEntryId(), fileVersion.getFileVersionId(),
				DLFileVersionPreviewConstants.STATUS_FAILURE) ||
			!DLProcessorRegistryUtil.isPreviewableSize(fileVersion)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbedVideoStatusMVCResourceCommand.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private VideoProcessor _videoProcessor;

}