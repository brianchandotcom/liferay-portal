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

import com.liferay.document.library.external.video.internal.ExternalVideo;
import com.liferay.document.library.external.video.internal.constants.ExternalVideoPortletKeys;
import com.liferay.document.library.external.video.internal.resolver.ExternalVideoResolver;
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
		"javax.portlet.name=" + ExternalVideoPortletKeys.EXTERNAL_VIDEO,
		"mvc.command.name=/document_library_external_video/get_external_video_fields"
	},
	service = MVCResourceCommand.class
)
public class GetExternalVideoFieldsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ExternalVideo externalVideo = _externalVideoResolver.resolve(
			ParamUtil.getString(resourceRequest, "externalVideoURL"));

		if (externalVideo != null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"DESCRIPTION",
					GetterUtil.getString(externalVideo.getDescription())
				).put(
					"HTML",
					GetterUtil.getString(externalVideo.getEmbeddableHTML())
				).put(
					"TITLE", GetterUtil.getString(externalVideo.getTitle())
				).put(
					"URL", GetterUtil.getString(externalVideo.getURL())
				));
		}
	}

	@Reference
	private ExternalVideoResolver _externalVideoResolver;

}