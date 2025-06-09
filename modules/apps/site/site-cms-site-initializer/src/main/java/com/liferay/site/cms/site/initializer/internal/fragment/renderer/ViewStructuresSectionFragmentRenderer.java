/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.site.cms.site.initializer.internal.display.context.StructuresSectionDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sam Ziemer
 */
@Component(service = FragmentRenderer.class)
public class ViewStructuresSectionFragmentRenderer
	extends BaseJSPSectionFragmentRenderer<StructuresSectionDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getLabelKey() {
		return "structures";
	}

	@Override
	protected StructuresSectionDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new StructuresSectionDisplayContext(httpServletRequest);
	}

	@Override
	protected String getJSPPath() {
		return "/view_structures_section.jsp";
	}

}