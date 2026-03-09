/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(service = FragmentRenderer.class)
public class EditWorkflowDefinitionFragmentRenderer
	extends BaseFragmentRenderer<Void> {

	@Override
	public String getCollectionKey() {
		return "workflow-definition";
	}

	@Override
	protected String getJSPPath() {
		return "/edit_workflow_definition.jsp";
	}

}