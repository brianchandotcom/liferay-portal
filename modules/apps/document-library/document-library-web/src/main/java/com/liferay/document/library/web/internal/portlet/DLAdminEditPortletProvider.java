/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.PortletProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"model.class.name=com.liferay.document.library.kernel.model.DLFileEntryType",
		"model.class.name=com.liferay.portal.kernel.repository.model.FileEntry",
		"model.class.name=com.liferay.portal.kernel.repository.model.FileShortcut",
		"model.class.name=com.liferay.portal.kernel.repository.model.Folder"
	},
	service = PortletProvider.class
)
public class DLAdminEditPortletProvider extends BasePortletProvider {

	@Override
	public String getPortletName() {
		return DLPortletKeys.DOCUMENT_LIBRARY_ADMIN;
	}

	@Override
	public Action[] getSupportedActions() {
		return _supportedActions;
	}

	private final Action[] _supportedActions = {Action.MANAGE};

}