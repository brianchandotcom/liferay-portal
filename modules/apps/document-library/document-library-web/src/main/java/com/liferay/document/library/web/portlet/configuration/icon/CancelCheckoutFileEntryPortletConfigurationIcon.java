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

package com.liferay.document.library.web.portlet.configuration.icon;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.document.library.web.display.context.logic.FileEntryDisplayContextHelper;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Roberto Díaz
 */
public class CancelCheckoutFileEntryPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public CancelCheckoutFileEntryPortletConfigurationIcon(
		PortletRequest portletRequest, FileEntry fileEntry) {

		super(portletRequest);

		_fileEntry = fileEntry;
	}

	@Override
	public String getMessage() {
		return "cancel-checkout[document]";
	}

	@Override
	public String getURL() {
		PortletURL cancelCheckoutURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			PortletRequest.ACTION_PHASE);

		cancelCheckoutURL.setParameter(
			"javax.portlet.action", "/document_library/edit_file_entry");
		cancelCheckoutURL.setParameter
			(Constants.CMD, Constants.CANCEL_CHECKOUT);
		cancelCheckoutURL.setParameter(
			"redirect", themeDisplay.getURLCurrent());
		cancelCheckoutURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		return cancelCheckoutURL.toString();
	}

	@Override
	public boolean isShow() {
		try {
			FileEntryDisplayContextHelper fileEntryDisplayContextHelper =
				new FileEntryDisplayContextHelper(
					themeDisplay.getPermissionChecker(), _fileEntry);

			return
				fileEntryDisplayContextHelper.
					isCancelCheckoutDocumentActionAvailable();
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	private final FileEntry _fileEntry;

}