/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.change.tracking.service.CTCollectionTemplateLocalService;
import com.liferay.change.tracking.web.internal.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
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

		if (ctCollectionTemplateId > 0) {
			CTCollectionTemplate ctCollectionTemplate =
				_ctCollectionTemplateLocalService.fetchCTCollectionTemplate(
					ctCollectionTemplateId);

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

		return "/publications/edit_ct_collection_template.jsp";
	}

	@Reference
	private CTCollectionTemplateLocalService _ctCollectionTemplateLocalService;

	@Reference
	private CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

}