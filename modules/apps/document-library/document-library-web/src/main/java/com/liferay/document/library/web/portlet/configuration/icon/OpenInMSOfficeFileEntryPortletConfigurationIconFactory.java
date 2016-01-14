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
import com.liferay.document.library.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIconFactory;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconFactory;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"path=/document_library/view_file_entry"
	},
	service = PortletConfigurationIconFactory.class
)
public class OpenInMSOfficeFileEntryPortletConfigurationIconFactory
	extends BasePortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(PortletRequest portletRequest) {
		try {
			FileEntry fileEntry = ActionUtil.getFileEntry(portletRequest);

			FileVersion fileVersion = ActionUtil.getFileVersion(
				portletRequest, fileEntry);

			return new OpenInMSOfficeFileEntryPortletConfigurationIcon(
				portletRequest, fileEntry, fileVersion);
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public double getWeight() {
		return 107.0;
	}

}