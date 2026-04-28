/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.portlet.action;

import com.liferay.osb.patcher.constants.PatcherFixConstants;
import com.liferay.osb.patcher.constants.PatcherPortletKeys;
import com.liferay.osb.patcher.constants.WorkflowConstants;
import com.liferay.osb.patcher.model.PatcherBuild;
import com.liferay.osb.patcher.model.PatcherFix;
import com.liferay.osb.patcher.permission.resource.PatcherPermission;
import com.liferay.osb.patcher.service.PatcherBuildLocalService;
import com.liferay.osb.patcher.service.PatcherFixLocalService;
import com.liferay.osb.patcher.util.JenkinsUtil;
import com.liferay.osb.patcher.util.PatcherBuildUtil;
import com.liferay.osb.patcher.util.PatcherUtil;
import com.liferay.osb.patcher.web.internal.validator.PatcherFixValidator;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"jakarta.portlet.name=" + PatcherPortletKeys.PATCHER,
		"mvc.command.name=/patcher/add_fixes"
	},
	service = MVCActionCommand.class
)
public class AddFixesMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!PatcherPermission.contains(
				themeDisplay.getPermissionChecker(), "FIXES", "ADD")) {

			throw new PrincipalException.MustHavePermission(
				themeDisplay.getUserId());
		}

		long patcherProductVersionId = ParamUtil.getLong(
			actionRequest, "patcherProductVersionId");
		long patcherProjectVersionId = ParamUtil.getLong(
			actionRequest, "patcherProjectVersionId");
		String patcherFixName = PatcherUtil.preparePatcherName(
			ParamUtil.getString(actionRequest, "patcherFixName"));
		String committish = ParamUtil.getString(actionRequest, "committish");
		String gitRemoteURL = ParamUtil.getString(
			actionRequest, "gitRemoteURL");
		boolean autoFix = ParamUtil.getBoolean(actionRequest, "autoFix");
		boolean workaround = ParamUtil.getBoolean(actionRequest, "workaround");

		PatcherFixValidator patcherFixValidator = new PatcherFixValidator(
			_portal.getHttpServletRequest(actionRequest));

		patcherFixValidator.validateAdd();

		int type = PatcherFixConstants.TYPE_PATCH;
	
		if (workaround) {
			type = PatcherFixConstants.TYPE_WORKAROUND;
		}
		else if (autoFix) {
			type = PatcherFixConstants.TYPE_AUTO_FIX;
		}

		PatcherFix patcherFix = _patcherFixLocalService.addPatcherFix(
			themeDisplay.getUserId(), patcherProductVersionId,
			patcherProjectVersionId, committish, gitRemoteURL, patcherFixName,
			type, WorkflowConstants.STATUS_FIX_ADDING);

		List<PatcherBuild> patcherBuilds =
			_patcherBuildLocalService.getPatcherFixPatcherBuilds(
				patcherFix.getPatcherFixId());

		for (PatcherBuild patcherBuild : patcherBuilds) {
			List<PatcherFix> incompletePatcherFixes =
				PatcherBuildUtil.getIncompletePatcherFixes(patcherBuild);

			if (incompletePatcherFixes.size() > 2) {
				continue;
			}

			patcherBuild = _patcherBuildLocalService.updateStatus(
				themeDisplay.getUserId(), patcherBuild.getPatcherBuildId(),
				PatcherBuildUtil.getNextPatcherBuildWorkflowStatus(
					patcherBuild, PatcherBuildUtil.isMergeOnly(patcherBuild)));

			PatcherBuildUtil.workflowParentPatcherBuild(
				themeDisplay.getUser(), patcherBuild);
		}

		JenkinsUtil.sendAgentJenkinsRequest(themeDisplay.getUser(), patcherFix);
	}

	@Reference
	private PatcherBuildLocalService _patcherBuildLocalService;

	@Reference
	private PatcherFixLocalService _patcherFixLocalService;

	@Reference
	private Portal _portal;

}