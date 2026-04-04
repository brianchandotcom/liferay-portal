/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.frontend.data.set.view.list;

import com.liferay.ai.hub.web.internal.constants.AIHubFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.list.BaseListFDSView;

import org.osgi.service.component.annotations.Component;

/**
 * @author José Abelenda
 */
@Component(
	property = {
		"frontend.data.set.name=" + AIHubFDSNames.AGENT_DEFINITIONS,
		"frontend.data.set.name=" + AIHubFDSNames.CHATBOTS,
		"frontend.data.set.name=" + AIHubFDSNames.CONTENT_RETRIEVERS,
		"frontend.data.set.name=" + AIHubFDSNames.INSTRUCTION_DEFINITIONS
	},
	service = FDSView.class
)
public class ListFDSView extends BaseListFDSView {

	@Override
	public String getDescription() {
		return "description";
	}

	@Override
	public String getTitle() {
		return "title";
	}

}