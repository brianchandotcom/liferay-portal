/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.headless.asset.library.resource.v1_0.AssetLibraryResource;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.site.cms.site.initializer.internal.display.context.SpacesSectionDisplayContext;

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

		SpacesSectionDisplayContext spacesSectionDisplayContext =
			new SpacesSectionDisplayContext(
				_assetLibraryResourceFactory, httpServletRequest, _jsonFactory,
				_portletResourcePermission);

		if (PortalRunMode.isTestMode()) {
			httpServletRequest.setAttribute(
				SpacesSectionDisplayContext.class.getName(),
				spacesSectionDisplayContext);
		}

		return spacesSectionDisplayContext.getProps();
	}

	@Reference
	private AssetLibraryResource.Factory _assetLibraryResourceFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(target = "(resource.name=" + DepotConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}