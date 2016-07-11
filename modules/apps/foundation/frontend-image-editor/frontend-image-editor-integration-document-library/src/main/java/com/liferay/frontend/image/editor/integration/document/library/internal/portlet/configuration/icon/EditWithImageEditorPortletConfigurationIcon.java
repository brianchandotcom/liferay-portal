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

package com.liferay.frontend.image.editor.integration.document.library.internal.portlet.configuration.icon;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.frontend.image.editor.integration.document.library.internal.constants.ImageEditorIntegrationDLWebKeys;
import com.liferay.frontend.image.editor.integration.document.library.internal.display.context.logic.ImageEditorDLDisplayContextHelper;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"path=/document_library/view_file_entry"
	},
	service = PortletConfigurationIcon.class
)
public class EditWithImageEditorPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	@Override
	public String getJspPath() {
		return "/image_editor/configuration/icon/edit_image_editor.jsp";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)),
			"edit-with-image-editor");
	}

	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public double getWeight() {
		return 106;
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		try {
			request.setAttribute(
				ImageEditorIntegrationDLWebKeys.
					IMAGE_EDITOR_INTEGRATION_DL_FILE_VERSION,
				getFileVersion(request));

			return super.include(request, response);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				portletRequest);

			FileVersion fileVersion = getFileVersion(request);

			ImageEditorDLDisplayContextHelper
				imageEditorDLDisplayContextHelper =
					new ImageEditorDLDisplayContextHelper(fileVersion, request);

			if (imageEditorDLDisplayContextHelper.isShowImageEditorAction()) {
				return true;
			}

			return false;
		}
		catch (Exception pe) {
		}

		return false;
	}

	@Reference(unbind = "-")
	public void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.image.editor.integration.document.library)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	protected FileVersion getFileVersion(HttpServletRequest request)
		throws Exception {

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		long fileEntryId = ParamUtil.getLong(portletRequest, "fileEntryId");

		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		String version = ParamUtil.getString(portletRequest, "version");

		FileVersion fileVersion = null;

		if (Validator.isNotNull(version)) {
			fileVersion = fileEntry.getFileVersion(version);
		}
		else {
			fileVersion = fileEntry.getFileVersion();
		}

		return fileVersion;
	}

	private DLAppService _dlAppService;

}