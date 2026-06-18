/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.portlet.action;

import com.liferay.osb.patcher.constants.PatcherPortletKeys;
import com.liferay.osb.patcher.model.PatcherBuild;
import com.liferay.osb.patcher.permission.resource.PatcherPermission;
import com.liferay.osb.patcher.service.PatcherBuildLocalService;
import com.liferay.osb.patcher.util.PatcherBuildUtil;
import com.liferay.osb.patcher.util.PatcherUtil;
import com.liferay.osb.patcher.web.internal.validator.PatcherBuildValidator;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"jakarta.portlet.name=" + PatcherPortletKeys.PATCHER,
		"mvc.command.name=/patcher/add_builds"
	},
	service = MVCActionCommand.class
)
public class AddBuildsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!PatcherPermission.contains(
				themeDisplay.getPermissionChecker(), "BUILDS", "ADD")) {

			throw new PrincipalException.MustHavePermission(
				themeDisplay.getUserId());
		}

		long patcherProductVersionId = ParamUtil.getLong(
			actionRequest, "patcherProductVersionId");
		long patcherProjectVersionId = ParamUtil.getLong(
			actionRequest, "patcherProjectVersionId");
		String accountEntryCode = StringUtil.toUpperCase(
			ParamUtil.getString(actionRequest, "accountEntryCode"));
		String patcherBuildName = PatcherUtil.preparePatcherName(
			ParamUtil.getString(actionRequest, "patcherBuildName"));
		String supportTicket = ParamUtil.getString(
			actionRequest, "supportTicket");
		int type = ParamUtil.getInteger(actionRequest, "type");

		PatcherBuildValidator patcherBuildValidator = new PatcherBuildValidator(
			_portal.getHttpServletRequest(actionRequest));

		patcherBuildValidator.validateAdd();

		boolean useExistingHotfix = ParamUtil.getBoolean(
			actionRequest, "useExistingHotfix");

		PatcherBuild patcherBuild =
			_patcherBuildLocalService.preparePatcherBuild(
				themeDisplay.getUserId(), patcherProductVersionId,
				patcherProjectVersionId, accountEntryCode, type,
				themeDisplay.getLocale(), patcherBuildName, useExistingHotfix);

		PatcherBuildUtil.savePatcherBuild(
			themeDisplay.getUser(), patcherBuild, accountEntryCode,
			supportTicket,
			ParamUtil.getBoolean(actionRequest, "smokeTestOnly", true),
			ParamUtil.getBoolean(actionRequest, "mergeOnly"),
			useExistingHotfix);

		sendRedirect(
			actionRequest, actionResponse,
			PortletURLBuilder.createRenderURL(
				_portal.getLiferayPortletResponse(actionResponse)
			).setMVCRenderCommandName(
				"/patcher/view_builds"
			).setParameter(
				"patcherBuildId", patcherBuild.getPatcherBuildId()
			).toString());
	}

	@Reference
	private PatcherBuildLocalService _patcherBuildLocalService;

	@Reference
	private Portal _portal;

}