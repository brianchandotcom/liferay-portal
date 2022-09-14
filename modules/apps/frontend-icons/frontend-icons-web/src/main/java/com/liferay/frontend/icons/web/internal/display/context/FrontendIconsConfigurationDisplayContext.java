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

package com.liferay.frontend.icons.web.internal.display.context;

import com.liferay.frontend.icons.web.internal.model.FrontendIconsResource;
import com.liferay.frontend.icons.web.internal.model.FrontendIconsResourcePack;
import com.liferay.frontend.icons.web.internal.repository.FrontendIconsResourcePackRepository;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class FrontendIconsConfigurationDisplayContext {

	public FrontendIconsConfigurationDisplayContext(
		FrontendIconsResourcePackRepository frontendIconsResourcePackRepository,
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_frontendIconsResourcePackRepository =
			frontendIconsResourcePackRepository;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getProps() {
		return HashMapBuilder.<String, Object>put(
			"deleteIconsPackResourceURL",
			PortletURLBuilder.createActionURL(
				_renderResponse
			).setActionName(
				"/instance_settings/delete_frontend_icons_pack_resource"
			).buildString()
		).put(
			"deleteIconsPackURL",
			PortletURLBuilder.createActionURL(
				_renderResponse
			).setActionName(
				"/instance_settings/delete_frontend_icons_pack"
			).buildString()
		).put(
			"icons",
			() -> {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				for (FrontendIconsResourcePack frontendIconsResourcePack :
						_frontendIconsResourcePackRepository.
							getFrontendIconsResourcePacks(
								_themeDisplay.getCompanyId())) {

					JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

					for (FrontendIconsResource frontendIconsResource :
							frontendIconsResourcePack.
								getFrontendIconsResources()) {

						jsonArray.put(
							JSONUtil.put(
								"name", frontendIconsResource.getName()));
					}

					jsonObject.put(
						frontendIconsResourcePack.getName(),
						JSONUtil.put(
							"editable", frontendIconsResourcePack.isEditable()
						).put(
							"icons", jsonArray
						));
				}

				return jsonObject;
			}
		).put(
			"saveFromExistingIconsActionURL",
			PortletURLBuilder.createActionURL(
				_renderResponse
			).setActionName(
				"/instance_settings" +
					"/save_frontend_icons_pack_from_existing_icons"
			).buildString()
		).put(
			"saveFromSpritemapActionURL",
			PortletURLBuilder.createActionURL(
				_renderResponse
			).setActionName(
				"/instance_settings/save_frontend_icons_pack_from_spritemap"
			).buildString()
		).build();
	}

	private final FrontendIconsResourcePackRepository
		_frontendIconsResourcePackRepository;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}