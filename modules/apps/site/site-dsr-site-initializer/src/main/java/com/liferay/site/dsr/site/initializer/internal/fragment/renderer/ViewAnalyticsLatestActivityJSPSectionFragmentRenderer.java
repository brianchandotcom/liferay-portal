/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.dsr.site.initializer.internal.display.context.ViewAnalyticsLatestActivitySectionDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(service = FragmentRenderer.class)
public class ViewAnalyticsLatestActivityJSPSectionFragmentRenderer
	extends BaseJSPSectionFragmentRenderer
		<ViewAnalyticsLatestActivitySectionDisplayContext> {

	@Override
	public String getLabelKey() {
		return "latest-activity";
	}

	@Override
	protected ViewAnalyticsLatestActivitySectionDisplayContext
		getDisplayContext(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new ViewAnalyticsLatestActivitySectionDisplayContext(
			httpServletRequest,
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", themeDisplay.getCompanyId()),
			_objectEntryService);
	}

	@Override
	protected String getJSPPath() {
		return "/view_analytics_latest_activity.jsp";
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

}