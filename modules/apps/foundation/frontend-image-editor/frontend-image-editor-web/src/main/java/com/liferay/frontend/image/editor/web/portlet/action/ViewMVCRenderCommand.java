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

package com.liferay.frontend.image.editor.web.portlet.action;

import com.liferay.frontend.image.editor.capability.ImageEditorCapability;
import com.liferay.frontend.image.editor.web.constants.ImageEditorPortletKeys;
import com.liferay.frontend.image.editor.web.portlet.tracker.ImageEditorCapabilityTracker;
import com.liferay.frontend.image.editor.web.portlet.tracker.ImageEditorCapabilityTracker.ImageEditorCapabilityInformation;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ImageEditorPortletKeys.IMAGE_EDITOR,
		"mvc.command.name=/", "mvc.command.name=/image_editor/view"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		Template template = getTemplate(renderRequest);

		Map<String, Object> capabilitiesContext = new HashMap<>();

		capabilitiesContext.put(
			"tools", getImageEditorToolsContext(renderRequest));

		template.put("capabilities", capabilitiesContext);

		String imageEditorURL = ParamUtil.getString(
			renderRequest, "imageEditorURL");

		template.put("image", imageEditorURL);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		template.put("pathThemeImages", themeDisplay.getPathThemeImages());

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				themeDisplay.getLanguageId());

		Map<String, Object> strings = new HashMap<>();

		for (String key : resourceBundle.keySet()) {
			strings.put(key, LanguageUtil.get(resourceBundle, key));
		}

		template.put("strings", strings);

		return "ImageEditor";
	}

	protected List<Map<String, Object>> getImageEditorToolsContext(
		RenderRequest renderRequest) {

		List<Map<String, Object>> imageEditorToolsContext =
			new ArrayList<Map<String, Object>>();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<ImageEditorCapabilityInformation>
			toolImageEditorCapabilityInformations =
				_imageEditorCapabilityTracker.getCapabilities("tool");

		if (toolImageEditorCapabilityInformations == null) {
			return imageEditorToolsContext;
		}

		List<List<ImageEditorCapabilityInformation>>
			imageEditorCapabilityInformationsList =
				getImageEditorCapabilityInformationsList(
					toolImageEditorCapabilityInformations);

		for (List<ImageEditorCapabilityInformation>
				imageEditorCapabilityInformations :
					imageEditorCapabilityInformationsList) {

			Map<String, Object> context = new HashMap<>();

			List<Map<String, Object>> controls =
				new ArrayList<Map<String, Object>>();
			String icon = StringPool.BLANK;

			for (ImageEditorCapabilityInformation
					imageEditorCapabilityInformation :
						imageEditorCapabilityInformations) {

				Map<String, Object> control = new HashMap<>();

				ImageEditorCapability imageEditorCapability =
					imageEditorCapabilityInformation.getImageEditorCapability();

				control.put(
					"label",
					imageEditorCapability.getLabel(themeDisplay.getLocale()));

				ServletContext servletContext =
					imageEditorCapability.getServletContext();

				control.put(
					"modulePath",
					servletContext.getContextPath());

				Map<String, Object> properties =
					imageEditorCapabilityInformation.getProperties();

				String variant = GetterUtil.getString(
					properties.get(
						"com.liferay.frontend.image.editor.capability." +
							"controls"));

				control.put("variant", variant);

				HttpServletRequest httpServletRequest =
					PortalUtil.getHttpServletRequest(renderRequest);

				imageEditorCapability.prepareContext(
					control, httpServletRequest);

				controls.add(control);

				icon = GetterUtil.getString(
					properties.get(
						"com.liferay.frontend.image.editor.capability.icon"));
			}

			context.put("controls", controls);
			context.put("icon", icon);

			imageEditorToolsContext.add(context);
		}

		return imageEditorToolsContext;
	}

	protected Template getTemplate(RenderRequest renderRequest) {
		return (Template)renderRequest.getAttribute(WebKeys.TEMPLATE);
	}

	protected List<List<ImageEditorCapabilityInformation>>
		getImageEditorCapabilityInformationsList(
			List<ImageEditorCapabilityInformation>
				imageEditorCapabilityInformations) {

		Map<String, List<ImageEditorCapabilityInformation>>
			imageEditorCapabilityInformationsMap = new HashMap<>();

		for (ImageEditorCapabilityInformation imageEditorCapabilityInformation :
				imageEditorCapabilityInformations) {

			Map<String, Object> properties =
				imageEditorCapabilityInformation.getProperties();

			String category = GetterUtil.getString(
				properties.get(
					"com.liferay.frontend.image.editor.capability.category"));

			if (!imageEditorCapabilityInformationsMap.containsKey(
					category)) {

				imageEditorCapabilityInformationsMap.put(
					category,
					new ArrayList<ImageEditorCapabilityInformation>());
			}

			List<ImageEditorCapabilityInformation>
				curImageEditorCapabilityInformations =
					imageEditorCapabilityInformationsMap.get(category);

			curImageEditorCapabilityInformations.add(
				imageEditorCapabilityInformation);
		}

		return new ArrayList<>(imageEditorCapabilityInformationsMap.values());
	}

	@Reference
	private ImageEditorCapabilityTracker _imageEditorCapabilityTracker;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.frontend.image.editor.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}