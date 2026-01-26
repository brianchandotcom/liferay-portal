/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marko Cikos
 */
@Component(service = FragmentRenderer.class)
public class AdvancedFiltersFDSFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sample";
	}

	@Override
	public String getIcon() {
		return "filter";
	}

	@Override
	public String getKey() {
		return "advanced-filters-fds-sample";
	}

	@Override
	public String getLabel(Locale locale) {
		return "Advanced FDS Filters";
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		_reactRenderer.renderReact(
			new ComponentDescriptor(
				"{AdvancedFilters} from frontend-data-set-sample-web"),
			new HashMap<>(), httpServletRequest, printWriter);
	}

	@Reference
	private ReactRenderer _reactRenderer;

}