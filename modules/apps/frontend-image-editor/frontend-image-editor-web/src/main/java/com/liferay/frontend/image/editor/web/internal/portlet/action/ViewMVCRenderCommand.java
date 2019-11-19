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

package com.liferay.frontend.image.editor.web.internal.portlet.action;

import com.liferay.frontend.image.editor.capability.ImageEditorCapability;
import com.liferay.frontend.image.editor.web.internal.constants.ImageEditorPortletKeys;
import com.liferay.frontend.image.editor.web.internal.portlet.tracker.ImageEditorCapabilityTracker;
import com.liferay.frontend.image.editor.web.internal.portlet.tracker.ImageEditorCapabilityTracker.ImageEditorCapabilityDescriptor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;

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

		Map<String, Object> imageEditorCapabilitiesContext =
			HashMapBuilder.<String, Object>put(
				"tools", getImageEditorToolsContexts(renderRequest)
			).build();

		template.put("imageEditorCapabilities", imageEditorCapabilitiesContext);

		String entityURL = ParamUtil.getString(renderRequest, "entityURL");

		template.put("image", entityURL);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		template.put("pathThemeImages", themeDisplay.getPathThemeImages());

		String eventName = ParamUtil.getString(renderRequest, "eventName");

		template.put("saveEventName", eventName);

		String saveFileName = ParamUtil.getString(
			renderRequest, "saveFileName");

		template.put("saveFileName", saveFileName);

		String saveMimeType = ParamUtil.getString(
			renderRequest, "saveMimeType");

		template.put("saveMimeType", saveMimeType);

		String saveParamName = ParamUtil.getString(
			renderRequest, "saveParamName");

		template.put("saveParamName", saveParamName);

		String saveURL = ParamUtil.getString(renderRequest, "saveURL");

		template.put("saveURL", saveURL);

		return "ImageEditor";
	}

	protected Map<String, List<ImageEditorCapabilityDescriptor>>
		getImageEditorCapabilityDescriptorsList(
			List<ImageEditorCapabilityDescriptor>
				imageEditorCapabilityDescriptors) {

		Map<String, List<ImageEditorCapabilityDescriptor>>
			imageEditorCapabilityDescriptorsMap = new HashMap<>();

		for (ImageEditorCapabilityDescriptor imageEditorCapabilityDescriptor :
				imageEditorCapabilityDescriptors) {

			Map<String, Object> properties =
				imageEditorCapabilityDescriptor.getProperties();

			String category = GetterUtil.getString(
				properties.get(
					"com.liferay.frontend.image.editor.capability.category"));

			if (!imageEditorCapabilityDescriptorsMap.containsKey(category)) {
				imageEditorCapabilityDescriptorsMap.put(
					category, new ArrayList<ImageEditorCapabilityDescriptor>());
			}

			List<ImageEditorCapabilityDescriptor>
				curImageEditorCapabilityDescriptors =
					imageEditorCapabilityDescriptorsMap.get(category);

			curImageEditorCapabilityDescriptors.add(
				imageEditorCapabilityDescriptor);
		}

		return imageEditorCapabilityDescriptorsMap;
	}

	protected List<Map<String, Object>> getImageEditorToolsContexts(
		RenderRequest renderRequest) {

		List<Map<String, Object>> imageEditorToolsContexts = new ArrayList<>();

		List<ImageEditorCapabilityDescriptor>
			toolImageEditorCapabilityDescriptors =
				_imageEditorCapabilityTracker.
					getImageEditorCapabilityDescriptors("tool");

		if (toolImageEditorCapabilityDescriptors == null) {
			return imageEditorToolsContexts;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			renderRequest.getLocale(), getClass());

		Map<String, List<ImageEditorCapabilityDescriptor>>
			imageEditorCapabilityDescriptorsMap =
				getImageEditorCapabilityDescriptorsList(
					toolImageEditorCapabilityDescriptors);

		for (Map.Entry<String, List<ImageEditorCapabilityDescriptor>> entry :
				imageEditorCapabilityDescriptorsMap.entrySet()) {

			List<Map<String, Object>> controlContexts = new ArrayList<>();
			String icon = StringPool.BLANK;

			for (ImageEditorCapabilityDescriptor
					imageEditorCapabilityDescriptor : entry.getValue()) {

				ImageEditorCapability imageEditorCapability =
					imageEditorCapabilityDescriptor.getImageEditorCapability();

				Map<String, Object> properties =
					imageEditorCapabilityDescriptor.getProperties();

				Map<String, Object> controlContext =
					HashMapBuilder.<String, Object>put(
						"label",
						imageEditorCapability.getLabel(themeDisplay.getLocale())
					).put(
						"modulePath",
						() -> {
							ServletContext servletContext =
								imageEditorCapability.getServletContext();

							return servletContext.getContextPath();
						}
					).put(
						"variant",
						GetterUtil.getString(
							properties.get(
								"com.liferay.frontend.image.editor." +
									"capability.controls"))
					).build();

				imageEditorCapability.prepareContext(
					controlContext,
					_portal.getHttpServletRequest(renderRequest));

				controlContexts.add(controlContext);

				icon = GetterUtil.getString(
					properties.get(
						"com.liferay.frontend.image.editor.capability.icon"));
			}

			Map<String, Object> context = HashMapBuilder.<String, Object>put(
				"controls", controlContexts
			).put(
				"icon", icon
			).put(
				"title", LanguageUtil.get(resourceBundle, entry.getKey())
			).build();

			imageEditorToolsContexts.add(context);
		}

		return imageEditorToolsContexts;
	}

	protected Template getTemplate(RenderRequest renderRequest) {
		return (Template)renderRequest.getAttribute(WebKeys.TEMPLATE);
	}

	@Reference
	private ImageEditorCapabilityTracker _imageEditorCapabilityTracker;

	@Reference
	private Portal _portal;

}