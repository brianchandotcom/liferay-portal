/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.display.context;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectEntryService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Gianmarco Brunialti Masera
 */
public class ViewAnalyticsRoomTrendAnalyticsSectionDisplayContext
	extends BaseAnalyticsSectionDisplayContext {

	public ViewAnalyticsRoomTrendAnalyticsSectionDisplayContext(
		HttpServletRequest httpServletRequest,
		ObjectDefinition objectDefinition,
		ObjectEntryService objectEntryService) {

		super(httpServletRequest, objectDefinition, objectEntryService);
	}

}