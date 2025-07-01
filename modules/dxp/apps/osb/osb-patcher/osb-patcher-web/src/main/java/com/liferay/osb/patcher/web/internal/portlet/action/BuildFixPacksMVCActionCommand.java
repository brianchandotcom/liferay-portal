/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.portlet.action;

import com.liferay.osb.patcher.constants.PatcherPortletKeys;
import com.liferay.osb.patcher.model.PatcherBuild;
import com.liferay.osb.patcher.model.PatcherFix;
import com.liferay.osb.patcher.model.PatcherFixPack;
import com.liferay.osb.patcher.service.PatcherBuildLocalService;
import com.liferay.osb.patcher.service.PatcherFixLocalService;
import com.liferay.osb.patcher.service.PatcherFixPackLocalService;
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
		"mvc.command.name=/patcher/build_fix_packs"
	},
	service = MVCActionCommand.class
)
public class BuildFixPacksMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long patcherFixPackId = ParamUtil.getLong(
			actionRequest, "patcherFixPackId");

		PatcherFixPack patcherFixPack =
			_patcherFixPackLocalService.getPatcherFixPack(patcherFixPackId);

		_validateBuild(patcherFixPack, themeDisplay);

		JenkinsUtil.sendDistJenkinsRequest(
			themeDisplay.getUser(),
			_patcherBuildLocalService.getPatcherBuild(
				patcherFixPack.getPatcherBuildId()));
	}

	private void _validateBuild(
			PatcherFixPack patcherFixPack, ThemeDisplay themeDisplay)
		throws Exception {

		String message = JenkinsUtil.validateJenkinsSetup();

		if (Validator.isNotNull(message)) {
			throw new Exception(message);
		}

		PatcherBuild patcherBuild = _patcherBuildLocalService.fetchPatcherBuild(
			patcherFixPack.getPatcherBuildId());

		if (patcherBuild == null) {
			throw new Exception(
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-fix-pack-cannot-be-built-because-it-is-not-merged"));
		}

		PatcherFix patcherFix = _patcherFixLocalService.getPatcherFix(
			patcherBuild.getPatcherFixId());

		if (Validator.isNull(patcherFix.getGitHash())) {
			throw new Exception(
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-fix-pack-cannot-be-built-because-it-is-not-merged"));
		}
	}

	@Reference
	private PatcherBuildLocalService _patcherBuildLocalService;

	@Reference
	private PatcherFixLocalService _patcherFixLocalService;

	@Reference
	private PatcherFixPackLocalService _patcherFixPackLocalService;

}