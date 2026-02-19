/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.fragment.renderer;

import com.liferay.ai.hub.web.internal.display.context.ViewAgentDefinitionsDisplayContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
@Component(service = FragmentRenderer.class)
public class ViewAgentDefinitionsFragmentRenderer
	extends BaseFragmentRenderer<ViewAgentDefinitionsDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "all-section");
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
	protected ViewAgentDefinitionsDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new ViewAgentDefinitionsDisplayContext(httpServletRequest);
	}

	@Override
	protected String getJSPPath() {
		return "/view_agent_definitions.jsp";
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

}