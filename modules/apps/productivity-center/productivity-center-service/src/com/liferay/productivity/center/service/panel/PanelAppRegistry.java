/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.productivity.center.service.panel;

import com.liferay.productivity.center.panel.PanelApp;
import com.liferay.productivity.center.panel.PanelCategory;
import com.liferay.productivity.center.service.util.PanelEntryServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Collections;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class PanelAppRegistry {

	public static Iterable<PanelApp> getPanelApps(PanelCategory panelCategory) {
		return _instance._getPanelApps(panelCategory);
	}

	private PanelAppRegistry() {
		_serviceTrackerMap.open();
	}

	private Iterable<PanelApp> _getPanelApps(PanelCategory panelCategory) {
		Iterable<PanelApp> panelItems = _serviceTrackerMap.getService(
			panelCategory.getKey());

		if (panelItems == null) {
			return Collections.emptyList();
		}

		return panelItems;
	}

	private static final PanelAppRegistry _instance = new PanelAppRegistry();

	private final ServiceTrackerMap<String, List<PanelApp>>
		_serviceTrackerMap = ServiceTrackerCollections.multiValueMap(
			PanelApp.class, "(panel.category=*)",
			PanelEntryServiceReferenceMapper.<PanelApp>create());

}