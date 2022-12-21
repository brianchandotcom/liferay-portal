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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.info.collection.provider.ConfigurableInfoCollectionProvider;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_edit_collection_configuration_url"
	},
	service = MVCResourceCommand.class
)
public class GetEditCollectionConfigurationURLMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String collectionKey = ParamUtil.getString(
			resourceRequest, "collectionKey");

		if (Validator.isBlank(collectionKey)) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				_jsonFactory.createJSONObject());

			return;
		}

		InfoCollectionProvider<?> infoCollectionProvider =
			_infoItemServiceRegistry.getInfoItemService(
				InfoCollectionProvider.class, collectionKey);

		if (infoCollectionProvider == null) {
			infoCollectionProvider =
				_infoItemServiceRegistry.getInfoItemService(
					RelatedInfoItemCollectionProvider.class, collectionKey);
		}

		if (!(infoCollectionProvider instanceof
				ConfigurableInfoCollectionProvider)) {

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				_jsonFactory.createJSONObject());

			return;
		}

		String itemId = ParamUtil.getString(resourceRequest, "itemId");

		String urlCurrent = HttpComponentsUtil.removeParameter(
			ParamUtil.getString(
				_portal.getOriginalServletRequest(
					_portal.getHttpServletRequest(resourceRequest)),
				"urlCurrent"),
			"itemId");

		urlCurrent = HttpComponentsUtil.addParameter(
			urlCurrent, "itemId", itemId);

		JSONObject jsonObject = JSONUtil.put(
			"url",
			PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					resourceRequest,
					ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/layout_content_page_editor/edit_collection_configuration"
			).setRedirect(
				urlCurrent
			).setParameter(
				"collectionKey", collectionKey
			).setParameter(
				"itemId", itemId
			).setParameter(
				"plid",
				() -> {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)resourceRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					return themeDisplay.getPlid();
				}
			).setParameter(
				"segmentsExperienceId",
				ParamUtil.getLong(resourceRequest, "segmentsExperienceId")
			).setParameter(
				"type", ParamUtil.getLong(resourceRequest, "type")
			).buildString());

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}