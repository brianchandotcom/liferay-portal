/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.fragment.renderer;

import com.liferay.ai.hub.web.internal.display.context.EditAgentDefinitionDisplayContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Davyson Melo
 * @author Feliphe Marinho
 */
@Component(service = FragmentRenderer.class)
public class EditAgentDefinitionFragmentRenderer
	extends BaseFragmentRenderer<EditAgentDefinitionDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "agent-definition";
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		return false;
	}

	@Override
	protected EditAgentDefinitionDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new EditAgentDefinitionDisplayContext(
			httpServletRequest, _portal);
	}

	@Override
	protected String getJSPPath() {
		return "/edit_agent_definition.jsp";
	}

	@Reference
	private Portal _portal;

}