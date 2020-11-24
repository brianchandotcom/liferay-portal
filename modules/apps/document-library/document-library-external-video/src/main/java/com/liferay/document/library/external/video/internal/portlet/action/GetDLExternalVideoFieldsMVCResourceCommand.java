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

package com.liferay.document.library.external.video.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.external.video.DLExternalVideo;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoConstants;
import com.liferay.document.library.external.video.resolver.DLExternalVideoResolver;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library_external_video/get_dl_external_video_fields"
	},
	service = MVCResourceCommand.class
)
public class GetDLExternalVideoFieldsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		DLExternalVideo dlExternalVideo = _dlExternalVideoResolver.resolve(
			ParamUtil.getString(resourceRequest, "dlExternalVideoURL"));

		if (dlExternalVideo != null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					DLExternalVideoConstants.DDM_FIELD_NAME_DESCRIPTION,
					GetterUtil.getString(dlExternalVideo.getDescription())
				).put(
					DLExternalVideoConstants.DDM_FIELD_NAME_HTML,
					GetterUtil.getString(dlExternalVideo.getEmbeddableHTML())
				).put(
					DLExternalVideoConstants.DDM_FIELD_NAME_THUMBNAIL_URL,
					GetterUtil.getString(dlExternalVideo.getThumbnailURL())
				).put(
					DLExternalVideoConstants.DDM_FIELD_NAME_TITLE,
					GetterUtil.getString(dlExternalVideo.getTitle())
				).put(
					DLExternalVideoConstants.DDM_FIELD_NAME_URL,
					GetterUtil.getString(dlExternalVideo.getURL())
				));
		}
	}

	@Reference
	private DLExternalVideoResolver _dlExternalVideoResolver;

}