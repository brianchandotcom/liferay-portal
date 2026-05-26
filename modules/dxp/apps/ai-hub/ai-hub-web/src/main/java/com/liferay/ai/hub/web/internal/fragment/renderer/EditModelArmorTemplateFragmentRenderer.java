/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.fragment.renderer;

import com.liferay.ai.hub.web.internal.display.context.EditModelArmorTemplateDisplayContext;
import com.liferay.fragment.renderer.FragmentRenderer;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author João Victor Alves
 */
@Component(service = FragmentRenderer.class)
public class EditModelArmorTemplateFragmentRenderer
	extends BaseFragmentRenderer<EditModelArmorTemplateDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "model-armor-template";
	}

	@Override
	protected EditModelArmorTemplateDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new EditModelArmorTemplateDisplayContext(httpServletRequest);
	}

	@Override
	protected String getJSPPath() {
		return "/edit_model_armor_template.jsp";
	}

}