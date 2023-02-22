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

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionTemplateLocalService;
import com.liferay.change.tracking.web.internal.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		"mvc.command.name=/change_tracking/add_ct_collection",
		"mvc.command.name=/change_tracking/edit_ct_collection",
		"mvc.command.name=/change_tracking/undo_ct_collection"
	},
	service = MVCRenderCommand.class
)
public class EditCTCollectionMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		long ctCollectionId = ParamUtil.getLong(
			renderRequest, "ctCollectionId");

		renderRequest.setAttribute(
			CTWebKeys.CT_COLLECTION,
			_ctCollectionLocalService.fetchCTCollection(ctCollectionId));

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<CTCollectionTemplate> ctCollectionTemplates =
			_ctCollectionTemplateLocalService.getCTCollectionTemplates(
				themeDisplay.getCompanyId(), 0, 100);

		renderRequest.setAttribute(
			CTWebKeys.CT_COLLECTION_TEMPLATES,
			jsonSerializer.serializeDeep(ctCollectionTemplates));

		renderRequest.setAttribute(
			CTWebKeys.DEFAULT_CT_COLLECTION_TEMPLATE_ID,
			_ctSettingsConfigurationHelper.getDefaultCTCollectionTemplateId(
				themeDisplay.getCompanyId()));

		Map<Long, JSONObject> map = new HashMap<>();

		for (CTCollectionTemplate ctCollectionTemplate :
				ctCollectionTemplates) {

			JSONObject jsonObject = ctCollectionTemplate.getJSONObject();

			jsonObject.put(
				"description",
				ctCollectionTemplate.getParsedPublicationDescription()
			).put(
				"name", ctCollectionTemplate.getParsedPublicationName()
			);

			map.put(
				ctCollectionTemplate.getCtCollectionTemplateId(), jsonObject);
		}

		renderRequest.setAttribute(
			CTWebKeys.CT_COLLECTION_TEMPLATES_DATA,
			_jsonFactory.looseSerializeDeep(map));

		return "/publications/edit_ct_collection.jsp";
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTCollectionTemplateLocalService _ctCollectionTemplateLocalService;

	@Reference
	private CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

	@Reference
	private JSONFactory _jsonFactory;

}