/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.display.context.ViewHomeSearchBarDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import jakarta.portlet.RenderRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.portal.workflow.manager.WorkflowLogManager;
import com.liferay.portal.workflow.comparator.WorkflowComparatorFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author Christian Dorado
 */
@Component(service = FragmentRenderer.class)
public class ViewHomeSearchBarJSPSectionFragmentRenderer
	extends BaseJSPSectionFragmentRenderer<ViewHomeSearchBarDisplayContext> {

	@Override
	public String getLabelKey() {
		return "home-search-bar";
	}

	@Override
	protected ViewHomeSearchBarDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new ViewHomeSearchBarDisplayContext(
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY), _workflowLogManager, _workflowComparatorFactory);
	}

	@Override
	protected String getJSPPath() {
		return "/view_home_search_bar.jsp";
	}
	
	@Reference
	private WorkflowLogManager _workflowLogManager;

	@Reference
	private WorkflowComparatorFactory _workflowComparatorFactory;

}
