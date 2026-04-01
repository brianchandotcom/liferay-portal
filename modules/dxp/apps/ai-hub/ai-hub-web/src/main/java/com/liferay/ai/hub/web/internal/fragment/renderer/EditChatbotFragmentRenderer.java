/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.fragment.renderer;

import com.liferay.ai.hub.web.internal.display.context.EditChatbotDisplayContext;
import com.liferay.fragment.renderer.FragmentRenderer;

import jakarta.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author José Abelenda
 */
@Component(service = FragmentRenderer.class)
public class EditChatbotFragmentRenderer
	extends BaseFragmentRenderer<EditChatbotDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "chatbot";
	}

	@Override
	protected EditChatbotDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		return new EditChatbotDisplayContext(httpServletRequest);
	}

	@Override
	protected String getJSPPath() {
		return "/edit_chatbot.jsp";
	}

}