/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.portlet.action;

import com.liferay.design.library.constants.DesignLibraryAdminPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Prates
 * @author Thiago Buarque
 */
@Component(
	property = {
		"jakarta.portlet.name=" + DesignLibraryAdminPortletKeys.DESIGN_LIBRARY_ADMIN,
		"mvc.command.name=/design_library/design_library_resources"
	},
	service = MVCRenderCommand.class
)
public class DesignLibraryResourcesMVCRenderCommand
	extends BaseDesignLibraryMVCRenderCommand {

	@Override
	protected String getActionId() {
		return ActionKeys.VIEW;
	}

	@Override
	protected String getPath() {
		return "/view_resources.jsp";
	}

}