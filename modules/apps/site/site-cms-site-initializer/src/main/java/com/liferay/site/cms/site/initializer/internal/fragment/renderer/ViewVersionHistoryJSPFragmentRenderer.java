/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.site.cms.site.initializer.internal.display.context.ViewVersionHistoryDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mikel Lorza
 */
@Component(service = FragmentRenderer.class)
public class ViewVersionHistoryJSPFragmentRenderer
	extends BaseJSPSectionFragmentRenderer<ViewVersionHistoryDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	protected ViewVersionHistoryDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new ViewVersionHistoryDisplayContext();
	}

	@Override
	protected String getLabelKey() {
		return "view-version-history";
	}

}