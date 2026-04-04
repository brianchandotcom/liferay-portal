/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.filter;

import com.liferay.ai.hub.web.internal.constants.AIHubFDSNames;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import org.osgi.service.component.annotations.Component;

import java.util.List;
import java.util.Locale;

/**
 * @author José Abelenda
 */
@Component(
	property = "frontend.data.set.name=" + AIHubFDSNames.CHATBOTS,
	service = FDSFilter.class
)
public class ChatbotStatusSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getId() {
		return "active";
	}

	@Override
	public String getLabel() {
		return "status";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return List.of(
			new SelectionFDSFilterItem("active", true),
			new SelectionFDSFilterItem("inactive", false));
	}

	@Override
	public boolean isMultiple() {
		return false;
	}

}