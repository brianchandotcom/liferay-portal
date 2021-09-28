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

import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemTemplatedRenderer;
import com.liferay.info.item.renderer.template.InfoItemRendererTemplate;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererTracker;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_available_list_item_renderers"
	},
	service = MVCResourceCommand.class
)
public class GetAvailableListItemRenderersMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String itemType = ParamUtil.getString(resourceRequest, "itemType");
		String itemSubtype = ParamUtil.getString(
			resourceRequest, "itemSubtype");

		String listStyle = ParamUtil.getString(resourceRequest, "listStyle");

		InfoListRenderer<?> infoListRenderer =
			_infoListRendererTracker.getInfoListRenderer(listStyle);

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		for (InfoItemRenderer<?> infoItemRenderer :
				infoListRenderer.getAvailableInfoItemRenderers()) {

			if (!infoItemRenderer.isAvailable()) {
				continue;
			}

			if (infoItemRenderer instanceof InfoItemTemplatedRenderer) {
				JSONArray templatesJSONArray =
					JSONFactoryUtil.createJSONArray();

				InfoItemTemplatedRenderer<Object> infoItemTemplatedRenderer =
					(InfoItemTemplatedRenderer<Object>)infoItemRenderer;

				List<InfoItemRendererTemplate> infoItemRendererTemplates =
					infoItemTemplatedRenderer.getInfoItemRendererTemplates(
						itemType, itemSubtype, themeDisplay.getLocale());

				if (infoItemRendererTemplates.isEmpty()) {
					continue;
				}

				Collections.sort(
					infoItemRendererTemplates,
					Comparator.comparing(InfoItemRendererTemplate::getLabel));

				for (InfoItemRendererTemplate infoItemRendererTemplate :
						infoItemRendererTemplates) {

					templatesJSONArray.put(
						JSONUtil.put(
							"key", infoItemRenderer.getKey()
						).put(
							"label", infoItemRendererTemplate.getLabel()
						).put(
							"templateKey",
							infoItemRendererTemplate.getTemplateKey()
						));
				}

				jsonArray.put(
					JSONUtil.put(
						"key", infoItemRenderer.getKey()
					).put(
						"label",
						infoItemTemplatedRenderer.
							getInfoItemRendererTemplatesGroupLabel(
								itemType, itemSubtype, themeDisplay.getLocale())
					).put(
						"templates", templatesJSONArray
					));
			}
			else {
				jsonArray.put(
					JSONUtil.put(
						"key", infoItemRenderer.getKey()
					).put(
						"label",
						infoItemRenderer.getLabel(themeDisplay.getLocale())
					));
			}
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	@Reference
	private InfoListRendererTracker _infoListRendererTracker;

}