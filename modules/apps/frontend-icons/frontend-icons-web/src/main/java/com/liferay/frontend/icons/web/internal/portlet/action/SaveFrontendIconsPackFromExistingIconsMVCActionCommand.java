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

package com.liferay.frontend.icons.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.frontend.icons.web.internal.model.FrontendIconsResource;
import com.liferay.frontend.icons.web.internal.model.FrontendIconsResourcePack;
import com.liferay.frontend.icons.web.internal.repository.FrontendIconsResourcePackRepository;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/instance_settings/save_frontend_icons_pack_from_existing_icons"
	},
	service = MVCActionCommand.class
)
public class SaveFrontendIconsPackFromExistingIconsMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(themeDisplay.getCompanyId())) {
			SessionErrors.add(actionRequest, PrincipalException.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		String iconPackName = ParamUtil.getString(
			actionRequest, "iconPackName");

		String icons = ParamUtil.getString(actionRequest, "icons");

		JSONObject iconsJSONObject = JSONFactoryUtil.createJSONObject(icons);

		Map<String, FrontendIconsResourcePack> frontendIconsResourcePacks =
			_getFrontendIconsResourcePacks(themeDisplay.getCompanyId());

		FrontendIconsResourcePack frontendIconsResourcePack =
			frontendIconsResourcePacks.getOrDefault(
				iconPackName, new FrontendIconsResourcePack(iconPackName));

		for (String key : iconsJSONObject.keySet()) {
			FrontendIconsResourcePack existingIconsResourcePack =
				frontendIconsResourcePacks.get(key);

			if (existingIconsResourcePack == null) {
				continue;
			}

			List<String> iconNames = JSONUtil.toStringList(
				iconsJSONObject.getJSONArray(key));

			iconNames.forEach(
				iconName -> {
					Optional<FrontendIconsResource>
						frontendIconsResourceOptional =
							existingIconsResourcePack.getFrontendIconsResource(
								iconName);

					frontendIconsResourceOptional.ifPresent(
						frontendIconsResourcePack::addFrontendIconResource);
				});
		}

		_frontendIconsResourcePackRepository.addFrontendIconsResourcePack(
			themeDisplay.getCompanyId(), frontendIconsResourcePack);

		Collection<FrontendIconsResource> frontendIconsResources =
			frontendIconsResourcePack.getFrontendIconsResources();

		JSONArray iconsJSONArray = JSONFactoryUtil.createJSONArray();

		for (FrontendIconsResource frontendIconsResource :
				frontendIconsResources) {

			iconsJSONArray.put(
				JSONUtil.put("name", frontendIconsResource.getId()));
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			JSONUtil.put(
				"editable", true
			).put(
				"icons", iconsJSONArray
			));
	}

	private Map<String, FrontendIconsResourcePack>
			_getFrontendIconsResourcePacks(long companyId)
		throws Exception {

		Map<String, FrontendIconsResourcePack> frontendIconsResourcePackMap =
			new HashMap<>();

		List<FrontendIconsResourcePack> frontendIconsResourcePacks =
			_frontendIconsResourcePackRepository.getFrontendIconsResourcePacks(
				companyId);

		for (FrontendIconsResourcePack frontendIconsResourcePack :
				frontendIconsResourcePacks) {

			frontendIconsResourcePackMap.put(
				frontendIconsResourcePack.getName(), frontendIconsResourcePack);
		}

		return frontendIconsResourcePackMap;
	}

	@Reference
	private FrontendIconsResourcePackRepository
		_frontendIconsResourcePackRepository;

	@Reference
	private Portal _portal;

}