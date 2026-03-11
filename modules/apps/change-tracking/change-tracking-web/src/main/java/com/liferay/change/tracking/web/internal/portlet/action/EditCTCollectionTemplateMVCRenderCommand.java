/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.change.tracking.service.CTCollectionTemplateLocalService;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	property = {
		"jakarta.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/edit_ct_collection_template"
	},
	service = MVCRenderCommand.class
)
public class EditCTCollectionTemplateMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		long ctCollectionTemplateId = ParamUtil.getLong(
			renderRequest, "ctCollectionTemplateId");

		if (ctCollectionTemplateId == 0) {
			return "/publications/edit_ct_collection_template.jsp";
		}

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			CTCollectionTemplate ctCollectionTemplate =
				_ctCollectionTemplateLocalService.getCTCollectionTemplate(
					ctCollectionTemplateId);

			_ctCollectionTemplateModelResourcePermission.check(
				themeDisplay.getPermissionChecker(), ctCollectionTemplate,
				ActionKeys.VIEW);

			renderRequest.setAttribute(
				CTWebKeys.CT_COLLECTION_TEMPLATE, ctCollectionTemplate);

			CTSettingsConfiguration ctSettingsConfiguration =
				_ctSettingsConfigurationHelper.getCTSettingsConfiguration(
					ctCollectionTemplate.getCompanyId());

			if (ctCollectionTemplateId ==
					ctSettingsConfiguration.defaultCTCollectionTemplateId()) {

				renderRequest.setAttribute(
					CTWebKeys.DEFAULT_CT_COLLECTION_TEMPLATE, Boolean.TRUE);
			}

			if (ctCollectionTemplateId ==
					ctSettingsConfiguration.
						defaultSandboxCTCollectionTemplateId()) {

				renderRequest.setAttribute(
					CTWebKeys.DEFAULT_SANDBOX_CT_COLLECTION_TEMPLATE,
					Boolean.TRUE);
			}
		}
		catch (Exception exception) {
			SessionErrors.add(renderRequest, exception.getClass());

			return "/publications/error.jsp";
		}

		return "/publications/edit_ct_collection_template.jsp";
	}

	@Reference
	private CTCollectionTemplateLocalService _ctCollectionTemplateLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollectionTemplate)"
	)
	private ModelResourcePermission<CTCollectionTemplate>
		_ctCollectionTemplateModelResourcePermission;

	@Reference
	private CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

}