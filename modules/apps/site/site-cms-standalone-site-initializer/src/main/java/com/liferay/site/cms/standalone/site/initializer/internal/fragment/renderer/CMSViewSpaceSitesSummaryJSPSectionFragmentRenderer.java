/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.standalone.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "service.ranking:Integer=100", service = FragmentRenderer.class
)
public class CMSViewSpaceSitesSummaryJSPSectionFragmentRenderer
	implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getKey() {
		return "com.liferay.site.cms.site.initializer.internal.fragment." +
			"renderer.ViewSpaceSitesSummaryJSPSectionFragmentRenderer";
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		return false;
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {
	}

}