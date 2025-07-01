/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.portlet.action;

import com.liferay.osb.patcher.constants.PatcherPortletKeys;
import com.liferay.osb.patcher.service.PatcherBuildLocalService;
import com.liferay.osb.patcher.util.PatcherBuildUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

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
		"mvc.command.name=/patcher/release_builds"
	},
	service = MVCActionCommand.class
)
public class ReleaseBuildsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long patcherBuildId = ParamUtil.getLong(
			actionRequest, "patcherBuildId");
		boolean releaseToHelpCenter = ParamUtil.getBoolean(
			actionRequest, "releaseToHelpCenter");
		int status = ParamUtil.getInteger(actionRequest, "status");

		if (releaseToHelpCenter) {
			PatcherBuildUtil.releasePatcherBuild(
				_patcherBuildLocalService.getPatcherBuild(patcherBuildId));
		}

		_patcherBuildLocalService.updateStatus(patcherBuildId, status);
	}

	@Reference
	private PatcherBuildLocalService _patcherBuildLocalService;

}