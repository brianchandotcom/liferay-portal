/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.web.internal.portlet.action;

import com.liferay.osb.patcher.constants.PatcherPortletKeys;
import com.liferay.osb.patcher.service.PatcherBuildLocalService;
import com.liferay.osb.patcher.util.PatcherBuildRelUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import jakarta.portlet.PortletException;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"jakarta.portlet.name=" + PatcherPortletKeys.PATCHER,
		"mvc.command.name=/patcher/edit_qa_fields_builds"
	},
	service = MVCRenderCommand.class
)
public class EditQaFieldsBuildsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long patcherBuildId = ParamUtil.getLong(
			renderRequest, "patcherBuildId");

		try {
			if (PatcherBuildRelUtil.hasParentPatcherBuilds(
					_patcherBuildLocalService.getPatcherBuild(
						patcherBuildId))) {

				throw new PortalException(
					"the-action-cannot-be-performed-on-child-builds");
			}
		}
		catch (Exception exception) {
			if (exception instanceof PortalException) {
				SessionErrors.add(renderRequest, exception.getClass());

				return "/osb_patcher/views/error.jsp";
			}

			throw new PortletException(exception);
		}

		return "/osb_patcher/views/builds/edit_qa_fields.jsp";
	}

	@Reference
	private PatcherBuildLocalService _patcherBuildLocalService;

}