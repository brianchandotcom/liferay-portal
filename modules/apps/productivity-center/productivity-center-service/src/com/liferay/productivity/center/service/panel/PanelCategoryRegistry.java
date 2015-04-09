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

import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;
import com.liferay.productivity.center.panel.PanelCategory;
import com.liferay.productivity.center.service.util.PanelEntryServiceReferenceMapper;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = PanelCategoryRegistry.class)
public class PanelCategoryRegistry {

	public Iterable<PanelCategory> getPanelCategories(
		PanelCategory panelCategory) {

		Iterable<PanelCategory> panelCategories = _serviceTrackerMap.getService(
			panelCategory.getKey());

		if (panelCategories == null) {
			return Collections.emptyList();
		}

		return panelCategories;
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTrackerMap = ServiceTrackerMapFactory.multiValueMap(
			bundleContext, PanelCategory.class, "(panel.category=*)",
			PanelEntryServiceReferenceMapper.<PanelCategory>create());

		_serviceTrackerMap.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, List<PanelCategory>> _serviceTrackerMap;

}