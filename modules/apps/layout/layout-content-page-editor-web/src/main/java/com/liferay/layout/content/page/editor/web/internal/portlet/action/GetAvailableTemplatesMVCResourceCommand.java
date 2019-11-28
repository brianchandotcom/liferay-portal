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

import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.info.item.provider.InfoItemDDMTemplateProvider;
import com.liferay.info.item.provider.InfoItemDDMTemplateProviderTracker;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/get_available_templates"
	},
	service = MVCResourceCommand.class
)
public class GetAvailableTemplatesMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String className = ParamUtil.getString(resourceRequest, "className");
		long classPK = ParamUtil.getLong(resourceRequest, "classPK");

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<InfoItemRenderer> infoItemRenderers =
			_infoItemRendererTracker.getInfoItemRenderers(className);

		for (InfoItemRenderer infoItemRenderer : infoItemRenderers) {
			JSONObject jsonObject = JSONUtil.put(
				"infoItemRendererKey", infoItemRenderer.getKey()
			).put(
				"label", infoItemRenderer.getLabel(themeDisplay.getLocale())
			);

			jsonArray.put(jsonObject);
		}

		InfoItemDDMTemplateProvider infoItemDDMTemplateProvider =
			_infoItemDDMTemplateProviderTracker.getInfoItemDDMTemplateProvider(
				className);

		if (infoItemDDMTemplateProvider != null) {
			InfoDisplayContributor infoDisplayContributor =
				_infoDisplayContributorTracker.getInfoDisplayContributor(
					className);

			if (infoDisplayContributor == null) {
				JSONPortletResponseUtil.writeJSON(
					resourceRequest, resourceResponse, jsonArray);

				return;
			}

			InfoDisplayObjectProvider infoDisplayObjectProvider =
				infoDisplayContributor.getInfoDisplayObjectProvider(classPK);

			if (infoDisplayObjectProvider == null) {
				JSONPortletResponseUtil.writeJSON(
					resourceRequest, resourceResponse, jsonArray);

				return;
			}

			List<DDMTemplate> ddmTemplates =
				infoItemDDMTemplateProvider.getDDMTemplates(
					infoDisplayObjectProvider.getDisplayObject());

			ddmTemplates.forEach(
				ddmTemplate -> {
					JSONObject jsonObject = JSONUtil.put(
						"ddmTemplateKey", ddmTemplate.getTemplateKey()
					).put(
						"label", ddmTemplate.getName(themeDisplay.getLocale())
					);

					jsonArray.put(jsonObject);
				});
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private InfoItemDDMTemplateProviderTracker
		_infoItemDDMTemplateProviderTracker;

	@Reference
	private InfoItemRendererTracker _infoItemRendererTracker;

}