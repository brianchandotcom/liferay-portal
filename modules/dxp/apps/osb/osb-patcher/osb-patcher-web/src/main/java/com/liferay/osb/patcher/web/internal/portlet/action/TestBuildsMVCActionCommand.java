/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.portlet.action;

import com.liferay.osb.patcher.constants.PatcherPortletKeys;
import com.liferay.osb.patcher.constants.WorkflowConstants;
import com.liferay.osb.patcher.model.PatcherBuild;
import com.liferay.osb.patcher.service.PatcherBuildLocalService;
import com.liferay.osb.patcher.util.JenkinsUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
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
		"mvc.command.name=/patcher/test_builds"
	},
	service = MVCActionCommand.class
)
public class TestBuildsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long patcherBuildId = ParamUtil.getLong(
			actionRequest, "patcherBuildId");
		int status = ParamUtil.getInteger(actionRequest, "status");

		PatcherBuild patcherBuild = _patcherBuildLocalService.getPatcherBuild(
			patcherBuildId);

		_validateTest(patcherBuild, themeDisplay);

		JenkinsUtil.sendTestJenkinsRequest(
			themeDisplay.getUser(), patcherBuild);

		_patcherBuildLocalService.updateQaStatus(patcherBuildId, status);
	}

	private void _validateTest(
			PatcherBuild patcherBuild, ThemeDisplay themeDisplay)
		throws Exception {

		if (patcherBuild.getStatus() !=
				WorkflowConstants.STATUS_BUILD_COMPLETE) {

			throw new Exception(
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-build-cannot-be-tested-because-its-status-is-not-" +
						"complete"));
		}

		if (Validator.isNull(patcherBuild.getFileName())) {
			throw new Exception(
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-build-cannot-be-tested-because-its-filename-is-not-" +
						"set"));
		}
	}

	@Reference
	private PatcherBuildLocalService _patcherBuildLocalService;

}