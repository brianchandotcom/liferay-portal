/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.fragment.renderer;

import com.liferay.ai.hub.web.internal.display.context.ViewActivityDashboardDisplayContext;
import com.liferay.fragment.renderer.FragmentRenderer;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Franca
 */
@Component(service = FragmentRenderer.class)
public class ViewActivityDashboardFragmentRenderer
	extends BaseFragmentRenderer<ViewActivityDashboardDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	protected ViewActivityDashboardDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new ViewActivityDashboardDisplayContext(httpServletRequest);
	}

	@Override
	protected String getJSPPath() {
		return "/view_activity_dashboard.jsp";
	}

}