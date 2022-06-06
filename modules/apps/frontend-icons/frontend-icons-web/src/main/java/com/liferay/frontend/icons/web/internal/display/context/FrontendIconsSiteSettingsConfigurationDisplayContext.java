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
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bryce Osterhaus
 */
public class FrontendIconsSiteSettingsConfigurationDisplayContext {

	public FrontendIconsSiteSettingsConfigurationDisplayContext(
		FrontendIconsResourcePackRepository frontendIconsResourcePackRepository,
		HttpServletRequest httpServletRequest, RenderResponse renderResponse,
		String[] selectedIconPacks) {

		_frontendIconsResourcePackRepository =
			frontendIconsResourcePackRepository;
		_renderResponse = renderResponse;
		_selectedIconPacks = selectedIconPacks;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getProps() {
		return HashMapBuilder.<String, Object>put(
			"allIconResourcePacks",
			() -> {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				List<FrontendIconsResourcePack> frontendIconsResourcePacks =
					_frontendIconsResourcePackRepository.
						getFrontendIconsResourcePacks(
							_themeDisplay.getCompanyId());

				for (FrontendIconsResourcePack frontendIconsResourcePack :
						frontendIconsResourcePacks) {

					JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

					List<FrontendIconsResource> frontendIconsResources =
						new ArrayList<>(
							frontendIconsResourcePack.
								getFrontendIconsResources());

					for (FrontendIconsResource frontendIconsResource :
							frontendIconsResources) {

						jsonArray.put(
							JSONUtil.put(
								"name", frontendIconsResource.getName()));
					}

					jsonObject.put(
						frontendIconsResourcePack.getName(),
						JSONUtil.put("icons", jsonArray));
				}

				return jsonObject;
			}
		).put(
			"saveSiteIconPacksURL",
			PortletURLBuilder.createActionURL(
				_renderResponse
			).setActionName(
				"/site_settings/save_site_frontend_icons_packs"
			).buildString()
		).put(
			"siteIconResourcePacks",
			() -> {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				for (String selectedIconPack : _selectedIconPacks) {
					jsonArray.put(StringUtil.toUpperCase(selectedIconPack));
				}

				return jsonArray;
			}
		).build();
	}

	private final FrontendIconsResourcePackRepository
		_frontendIconsResourcePackRepository;
	private final RenderResponse _renderResponse;
	private final String[] _selectedIconPacks;
	private final ThemeDisplay _themeDisplay;

}