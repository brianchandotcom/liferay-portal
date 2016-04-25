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

package com.liferay.image.editor.web.commands;

import com.liferay.image.editor.api.ImageEditorFeature;
import com.liferay.image.editor.web.ImageEditorFeatureTracker;
import com.liferay.image.editor.web.ImageEditorPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ImageEditorPortletKeys.NAME,
		"mvc.command.name=/", "mvc.command.name=View"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		Template template = getTemplate(renderRequest);

		List<Map<String, Object>> toolsContexts = new ArrayList<>();

		Map<String, Map<String, Object>> featuresProperties =
			_featureTracker.getFeaturesProperties();

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);

		for (String featureName : featuresProperties.keySet()) {
			Map<String, Object> context = new HashMap<>();

			Map<String, Object> properties = featuresProperties.get(
				featureName);

			String controls = GetterUtil.getString(
				properties.get("com.liferay.image.editor.tool.controls"));
			String icon = GetterUtil.getString(
				properties.get("com.liferay.image.editor.tool.icon"));
			String name = GetterUtil.getString(
				properties.get("com.liferay.image.editor.tool.name"));

			context.put("controls", controls);
			context.put("icon", icon);
			context.put("name", name);
			context.put("hasDropdown", false);

			ImageEditorFeature feature = _featureTracker.getFeature(
				featureName);

			feature.prepareContext(context, httpServletRequest);

			toolsContexts.add(context);
		}

		template.put("image", "http://localhost:8080/documents/20233/0/214H.jpg/44f148f9-adee-9020-dbc5-61c112d0bc26?t=1460307134146");
		template.put("tools", toolsContexts);

		return "ImageEditor";
	}

	protected Template getTemplate(RenderRequest renderRequest) {
		return (Template)renderRequest.getAttribute(WebKeys.TEMPLATE);
	}

	@Reference
	private ImageEditorFeatureTracker _featureTracker;
}