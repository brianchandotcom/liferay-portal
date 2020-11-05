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
import com.liferay.document.library.external.video.internal.constants.ExternalVideoConstants;
import com.liferay.document.library.external.video.internal.constants.ExternalVideoPortletKeys;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 * @author Sergio González
 * @author Alejandro Tardín
 */
public class ExternalVideoDLFilePicker implements DLFilePicker {

	public ExternalVideoDLFilePicker(
		HttpServletRequest httpServletRequest, String onFilePickCallback) {

		_httpServletRequest = httpServletRequest;
		_onFilePickCallback = onFilePickCallback;
	}

	@Override
	public String getDescriptionFieldName() {
		return ExternalVideoConstants.DDM_FIELD_NAME_DESCRIPTION;
	}

	@Override
	public String getIconFieldName() {
		return null;
	}

	@Override
	public String getJavaScript() throws PortalException {
		String templateId =
			"/com/liferay/document/library/external/video/internal/display" +
				"/context/dependencies/external_video_file_picker.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource templateResource = new URLTemplateResource(
			templateId, clazz.getResource(templateId));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, templateResource, false);

		template.put("getExternalVideoFieldsURL", _getExternalVideoFieldsURL());
		template.put(
			"namespace",
			PortalUtil.getPortletNamespace(
				ExternalVideoPortletKeys.EXTERNAL_VIDEO));
		template.put("onFilePickCallback", _onFilePickCallback);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	@Override
	public String getJavaScriptModuleName() {
		return "ExternalVideoPicker";
	}

	@Override
	public String getOnClickCallback() {
		return "openPicker";
	}

	@Override
	public String getTitleFieldName() {
		return ExternalVideoConstants.DDM_FIELD_NAME_TITLE;
	}

	private String _getExternalVideoFieldsURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest);

		ResourceURL getExternalVideoFieldsURL =
			(ResourceURL)requestBackedPortletURLFactory.createResourceURL(
				ExternalVideoPortletKeys.EXTERNAL_VIDEO);

		getExternalVideoFieldsURL.setResourceID(
			"/document_library_external_video/get_external_video_fields");

		return getExternalVideoFieldsURL.toString();
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _onFilePickCallback;

}