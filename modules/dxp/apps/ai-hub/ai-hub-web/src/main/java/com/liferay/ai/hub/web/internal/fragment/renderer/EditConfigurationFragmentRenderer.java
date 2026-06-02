/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.fragment.renderer;

import com.liferay.ai.hub.web.internal.display.context.EditConfigurationDisplayContext;
import com.liferay.fragment.renderer.FragmentRenderer;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pedro Leite
 */
@Component(service = FragmentRenderer.class)
public class EditConfigurationFragmentRenderer
	extends BaseFragmentRenderer<EditConfigurationDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "configuration";
	}

	@Override
	protected EditConfigurationDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new EditConfigurationDisplayContext(httpServletRequest);
	}

	@Override
	protected String getJSPPath() {
		return "/edit_configuration.jsp";
	}

}