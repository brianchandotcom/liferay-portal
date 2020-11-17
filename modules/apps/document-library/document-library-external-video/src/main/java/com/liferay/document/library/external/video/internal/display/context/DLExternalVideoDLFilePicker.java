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

package com.liferay.document.library.external.video.internal.display.context;

import com.liferay.document.library.display.context.DLFilePicker;
import com.liferay.document.library.external.video.DLExternalVideo;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoConstants;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoPortletKeys;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 * @author Sergio González
 * @author Alejandro Tardín
 */
public class DLExternalVideoDLFilePicker implements DLFilePicker {

	public DLExternalVideoDLFilePicker(
		DLExternalVideo dlExternalVideo, HttpServletRequest httpServletRequest,
		String onFilePickCallback) {

		_dlExternalVideo = dlExternalVideo;
		_httpServletRequest = httpServletRequest;
		_onFilePickCallback = onFilePickCallback;
	}

	@Override
	public String getCurrentIconURL() {
		if (_dlExternalVideo != null) {
			return _dlExternalVideo.getIconURL();
		}

		return StringPool.BLANK;
	}

	@Override
	public String getCurrentTitle() {
		if (_dlExternalVideo != null) {
			return GetterUtil.getString(
				_dlExternalVideo.getTitle(), _dlExternalVideo.getURL());
		}

		return StringPool.BLANK;
	}

	@Override
	public String getDescriptionFieldName() {
		return DLExternalVideoConstants.DDM_FIELD_NAME_DESCRIPTION;
	}

	@Override
	public String getIconFieldName() {
		return "ICON_URL";
	}

	@Override
	public String getJavaScript() throws PortalException {
		String templateId =
			"/com/liferay/document/library/external/video/internal/display" +
				"/context/dependencies/dl_external_video_file_picker.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource templateResource = new URLTemplateResource(
			templateId, clazz.getResource(templateId));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, templateResource, false);

		template.put(
			"getDLExternalVideoFieldsURL", _getDLExternalVideoFieldsURL());
		template.put(
			"namespace",
			PortalUtil.getPortletNamespace(
				DLExternalVideoPortletKeys.DL_EXTERNAL_VIDEO));
		template.put("onFilePickCallback", _onFilePickCallback);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	@Override
	public String getJavaScriptModuleName() {
		return "DLExternalVideoPicker";
	}

	@Override
	public String getOnClickCallback() {
		return "openPicker";
	}

	@Override
	public String getTitleFieldName() {
		return DLExternalVideoConstants.DDM_FIELD_NAME_TITLE;
	}

	@Override
	public boolean isCustomizedFileButtonVisible() {
		return false;
	}

	private String _getDLExternalVideoFieldsURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest);

		ResourceURL getDLExternalVideoFieldsURL =
			(ResourceURL)requestBackedPortletURLFactory.createResourceURL(
				DLExternalVideoPortletKeys.DL_EXTERNAL_VIDEO);

		getDLExternalVideoFieldsURL.setResourceID(
			"/document_library_external_video/get_dl_external_video_fields");

		return getDLExternalVideoFieldsURL.toString();
	}

	private final DLExternalVideo _dlExternalVideo;
	private final HttpServletRequest _httpServletRequest;
	private final String _onFilePickCallback;

}