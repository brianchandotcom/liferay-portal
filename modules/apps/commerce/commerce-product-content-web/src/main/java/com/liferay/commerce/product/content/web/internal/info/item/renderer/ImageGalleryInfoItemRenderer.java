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

package com.liferay.commerce.product.content.web.internal.info.item.renderer;

import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.content.util.CPMedia;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false,
	service = {ImageGalleryInfoItemRenderer.class, InfoItemRenderer.class}
)
public class ImageGalleryInfoItemRenderer
	implements InfoItemRenderer<CPDefinition> {

	@Override
	public String getKey() {
		return "cpDefinition-image-gallery";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "image-gallery");
	}

	@Override
	public void render(
		CPDefinition cpDefinition, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (cpDefinition == null) {
			return;
		}

		try {
			String randomKey = _portal.generateRandomKey(
				httpServletRequest, "product.gallery.info.item.renderer");

			String componentId = randomKey + "GalleryComponent";

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				"images",
				() -> {
					List<CPMedia> images = _cpContentHelper.getImages(
						cpDefinition.getCPDefinitionId(), themeDisplay);

					JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

					for (CPMedia cpMedia : images) {
						jsonArray.put(
							JSONUtil.put(
								"thumbnailURL", cpMedia.getThumbnailURL()
							).put(
								"title", cpMedia.getTitle()
							).put(
								"URL", cpMedia.getURL()
							));
					}

					return jsonArray;
				}
			).put(
				"namespace", componentId
			).put(
				"portletId",
				() -> {
					PortletDisplay portletDisplay =
						themeDisplay.getPortletDisplay();

					return portletDisplay.getRootPortletId();
				}
			).put(
				"viewCPAttachmentURL",
				() -> PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(
						httpServletRequest, CPDefinition.class.getName(),
						PortletProvider.Action.VIEW)
				).setMVCRenderCommandName(
					"/cp_content_web/view_cp_attachments"
				).setParameter(
					"cpDefinitionId", cpDefinition.getCPDefinitionId()
				).build()
			).build();

			_reactRenderer.renderReact(
				new ComponentDescriptor(
					"commerce-frontend-js/components/gallery/Gallery",
					componentId),
				data, httpServletRequest, httpServletResponse.getWriter());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference
	private CPContentHelper _cpContentHelper;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private ReactRenderer _reactRenderer;

}