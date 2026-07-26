/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.portlet.action;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.design.library.web.internal.constants.DesignLibraryConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletException;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Thiago Buarque
 */
public abstract class BaseDesignLibraryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				DesignLibraryConstants.DESIGN_LIBRARY_ENTRY,
				_getDepotEntry(renderRequest));
		}
		catch (PrincipalException principalException) {
			SessionErrors.add(renderRequest, principalException.getClass());

			return "/error.jsp";
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}

		return getPath();
	}

	protected abstract String getActionId();

	protected abstract String getPath();

	@Reference
	protected DepotEntryLocalService depotEntryLocalService;

	@Reference(target = "(model.class.name=com.liferay.depot.model.DepotEntry)")
	protected ModelResourcePermission<DepotEntry>
		depotEntryModelResourcePermission;

	private DepotEntry _getDepotEntry(RenderRequest renderRequest)
		throws PortalException {

		long designLibraryEntryId = ParamUtil.getLong(
			renderRequest, DesignLibraryConstants.DESIGN_LIBRARY_ENTRY_ID_KEY);

		DepotEntry depotEntry = depotEntryLocalService.fetchDepotEntry(
			designLibraryEntryId);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if ((depotEntry == null) ||
			(depotEntry.getCompanyId() != themeDisplay.getCompanyId()) ||
			(depotEntry.getType() != DepotConstants.TYPE_DESIGN_LIBRARY)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, DepotEntry.class.getName(),
				designLibraryEntryId, getActionId());
		}

		depotEntryModelResourcePermission.check(
			permissionChecker, depotEntry, getActionId());

		return depotEntry;
	}

}