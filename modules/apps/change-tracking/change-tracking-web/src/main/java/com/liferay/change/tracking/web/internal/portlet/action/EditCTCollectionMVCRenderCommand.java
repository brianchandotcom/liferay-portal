/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionTemplateLocalService;
import com.liferay.change.tracking.service.CTRemoteLocalService;
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

		long ctRemoteId = ParamUtil.getLong(renderRequest, "ctRemoteId");

		if (ctRemoteId != 0) {
			renderRequest.setAttribute(
				CTWebKeys.CT_REMOTE,
				_ctRemoteLocalService.fetchCTRemote(ctRemoteId));
		}

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<CTCollectionTemplate> ctCollectionTemplates =
			_ctCollectionTemplateLocalService.getCTCollectionTemplates(
				themeDisplay.getCompanyId(), 0, 100);

		renderRequest.setAttribute(
			CTWebKeys.CT_COLLECTION_TEMPLATES,
			jsonSerializer.serializeDeep(ctCollectionTemplates));

		if (ctCollectionId == 0) {
			renderRequest.setAttribute(
				CTWebKeys.DEFAULT_CT_COLLECTION_TEMPLATE_ID,
				_ctSettingsConfigurationHelper.getDefaultCTCollectionTemplateId(
					themeDisplay.getCompanyId()));
		}

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
	private CTRemoteLocalService _ctRemoteLocalService;

	@Reference
	private CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

	@Reference
	private JSONFactory _jsonFactory;

}