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

package com.liferay.layout.admin.web.internal.upload;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.BaseImageEditorUploadFileEntryHandler;
import com.liferay.upload.UniqueFileNameProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bárbara Cabrera
 */
@Component(
	service = LayoutUtilityPageEntryPreviewImageEditorUploadFileEntryHandler.class
)
public class LayoutUtilityPageEntryPreviewImageEditorUploadFileEntryHandler
	extends BaseImageEditorUploadFileEntryHandler {

	@Override
	protected void checkPermissions(UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long layoutUtilityPageEntryId = ParamUtil.getLong(
			uploadPortletRequest, "layoutUtilityPageEntryId");

		_layoutUtilityPageEntryModelResourcePermission.check(
			themeDisplay.getPermissionChecker(), layoutUtilityPageEntryId,
			ActionKeys.UPDATE);
	}

	@Override
	protected DLAppService getDLAppService() {
		return _dlAppService;
	}

	@Override
	protected String getFolderName() {
		return LayoutUtilityPageEntryPreviewImageEditorUploadFileEntryHandler.
			class.getName();
	}

	@Override
	protected UniqueFileNameProvider getUniqueFileNameProvider() {
		return _uniqueFileNameProvider;
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(model.class.name=com.liferay.layout.utility.page.model.LayoutUtilityPageEntry)"
	)
	private ModelResourcePermission<LayoutUtilityPageEntry>
		_layoutUtilityPageEntryModelResourcePermission;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}