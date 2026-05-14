/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.fragment.renderer;

import com.liferay.ai.hub.web.internal.display.context.HomeDashboardDisplayContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Davyson Melo
 */
@Component(service = FragmentRenderer.class)
public class HomeDashboardFragmentRenderer
	extends BaseFragmentRenderer<HomeDashboardDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "home-dashboard";
	}

	@Override
	protected HomeDashboardDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new HomeDashboardDisplayContext(
			_groupLocalService, httpServletRequest, _portal);
	}

	@Override
	protected String getJSPPath() {
		return "/home_dashboard.jsp";
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}