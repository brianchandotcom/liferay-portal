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

package com.liferay.style.book.web.internal.upload;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.constants.StyleBookActionKeys;
import com.liferay.style.book.constants.StyleBookConstants;
import com.liferay.upload.BaseImageEditorUploadFileEntryHandler;
import com.liferay.upload.UniqueFileNameProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	service = StyleBookEntryPreviewImageEditorUploadFileEntryHandler.class
)
public class StyleBookEntryPreviewImageEditorUploadFileEntryHandler
	extends BaseImageEditorUploadFileEntryHandler {

	@Override
	protected void checkPermissions(UploadPortletRequest uploadPortletRequest)
		throws PrincipalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_portletResourcePermission.check(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup(),
			StyleBookActionKeys.MANAGE_STYLE_BOOK_ENTRIES);
	}

	@Override
	protected DLAppService getDLAppService() {
		return _dlAppService;
	}

	@Override
	protected String getFolderName() {
		return StyleBookEntryPreviewImageEditorUploadFileEntryHandler.class.
			getName();
	}

	@Override
	protected UniqueFileNameProvider getUniqueFileNameProvider() {
		return _uniqueFileNameProvider;
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(resource.name=" + StyleBookConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private UniqueFileNameProvider _uniqueFileNameProvider;

}