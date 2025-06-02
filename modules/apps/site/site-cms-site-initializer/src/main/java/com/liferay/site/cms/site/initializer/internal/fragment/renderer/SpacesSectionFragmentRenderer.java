/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.depot.constants.DepotActionKeys;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.headless.asset.library.dto.v1_0.AssetLibrary;
import com.liferay.headless.asset.library.resource.v1_0.AssetLibraryResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.site.cms.site.initializer.internal.display.context.SpacesSectionDisplayContext;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = FragmentRenderer.class)
public class SpacesSectionFragmentRenderer
	extends BaseComponentSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	protected String getLabelKey() {
		return "Spaces";
	}

	@Override
	protected String getModuleName() {
		return "SpacesNavigation";
	}

	@Override
	protected Map<String, Object> getProps(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SpacesSectionDisplayContext spacesSectionDisplayContext =
			new SpacesSectionDisplayContext(
				_assetLibraryResourceFactory, httpServletRequest);

		if (PortalRunMode.isTestMode()) {
			httpServletRequest.setAttribute(
				SpacesSectionDisplayContext.class.getName(),
				spacesSectionDisplayContext);
		}

		Page<AssetLibrary> page = spacesSectionDisplayContext.getPage();

		return HashMapBuilder.<String, Object>put(
			"allSpacesURL",
			StringBundler.concat(
				themeDisplay.getPathFriendlyURLPublic(),
				GroupConstants.CMS_FRIENDLY_URL, "/all-spaces")
		).put(
			"assetLibraries",
			JSONUtil.toJSONArray(
				page.getItems(),
				assetLibrary -> JSONUtil.put(
					"id", assetLibrary.getId()
				).put(
					"name", assetLibrary.getName()
				).put(
					"settings",
					_jsonFactory.createJSONObject(
						_jsonFactory.looseSerialize(
							assetLibrary.getSettings()))
				).put(
					"url",
					ActionUtil.getSpaceURL(
						assetLibrary.getId(), themeDisplay)))
		).put(
			"assetLibrariesCount", page.getTotalCount()
		).put(
			"newSpaceURL",
			StringBundler.concat(
				themeDisplay.getPathFriendlyURLPublic(),
				GroupConstants.CMS_FRIENDLY_URL, "/new-space")
		).put(
			"showAddButton",
			_portletResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), DepotActionKeys.ADD_DEPOT_ENTRY)
		).build();
	}

	@Reference
	private AssetLibraryResource.Factory _assetLibraryResourceFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(target = "(resource.name=" + DepotConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}