/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.fragment.renderer;

import com.liferay.ai.hub.web.internal.display.context.EditContentRetrieverDisplayContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Davyson Melo
 */
@Component(service = FragmentRenderer.class)
public class EditContentRetrieverFragmentRenderer
	extends BaseFragmentRenderer<EditContentRetrieverDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = _groupLocalService.fetchGroup(
			themeDisplay.getScopeGroupId());

		if (group == null) {
			return false;
		}

		return true;
	}

	@Override
	protected EditContentRetrieverDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new EditContentRetrieverDisplayContext(
			_groupLocalService, httpServletRequest);
	}

	@Override
	protected String getJSPPath() {
		return "/edit_content_retriever.jsp";
	}

	@Reference
	private GroupLocalService _groupLocalService;

}