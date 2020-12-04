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

import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.document.library.video.external.shortcut.resolver.DLVideoExternalShortcutResolver;
import com.liferay.document.library.video.internal.constants.DLVideoConstants;
import com.liferay.document.library.video.internal.constants.DLVideoPortletKeys;
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
		"javax.portlet.name=" + DLVideoPortletKeys.DL_VIDEO,
		"mvc.command.name=/document_library_video/get_dl_video_external_shortcut_fields"
	},
	service = MVCResourceCommand.class
)
public class GetDLVideoExternalShortcutFieldsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		DLVideoExternalShortcut dlVideoExternalShortcut =
			_dlVideoExternalShortcutResolver.resolve(
				ParamUtil.getString(
					resourceRequest, "dlVideoExternalShortcutURL"));

		if (dlVideoExternalShortcut != null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					DLVideoConstants.DDM_FIELD_NAME_DESCRIPTION,
					GetterUtil.getString(
						dlVideoExternalShortcut.getDescription())
				).put(
					DLVideoConstants.DDM_FIELD_NAME_HTML,
					GetterUtil.getString(
						dlVideoExternalShortcut.getEmbeddableHTML())
				).put(
					DLVideoConstants.DDM_FIELD_NAME_THUMBNAIL_URL,
					GetterUtil.getString(
						dlVideoExternalShortcut.getThumbnailURL())
				).put(
					DLVideoConstants.DDM_FIELD_NAME_TITLE,
					GetterUtil.getString(dlVideoExternalShortcut.getTitle())
				).put(
					DLVideoConstants.DDM_FIELD_NAME_URL,
					GetterUtil.getString(dlVideoExternalShortcut.getURL())
				));
		}
	}

	@Reference
	private DLVideoExternalShortcutResolver _dlVideoExternalShortcutResolver;

}