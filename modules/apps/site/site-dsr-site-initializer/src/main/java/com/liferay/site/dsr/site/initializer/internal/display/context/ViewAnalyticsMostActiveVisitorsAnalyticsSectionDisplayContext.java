/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.display.context;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.site.dsr.site.initializer.internal.constants.DSRSiteInitializerFDSNames;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Gianmarco Brunialti Masera
 */
public class ViewAnalyticsMostActiveVisitorsAnalyticsSectionDisplayContext
	extends BaseAnalyticsSectionDisplayContext {

	public ViewAnalyticsMostActiveVisitorsAnalyticsSectionDisplayContext(
		HttpServletRequest httpServletRequest,
		ObjectDefinition objectDefinition,
		ObjectEntryService objectEntryService) {

		super(httpServletRequest, objectDefinition, objectEntryService);
	}

	@Override
	public Map<String, Object> getProps() {
		return HashMapBuilder.<String, Object>putAll(
			super.getProps()
		).put(
			"namespace", DSRSiteInitializerFDSNames.MOST_ACTIVE_VISITORS
		).build();
	}

}