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

import com.liferay.osgi.service.tracker.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;
import com.liferay.productivity.center.panel.PanelCategory;
import com.liferay.productivity.center.service.util.ParentPanelCategoryServiceReferenceMapper;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = PanelCategoryRegistry.class)
public class PanelCategoryRegistry {

	public Iterable<PanelCategory> getPanelCategories(
		PanelCategory parentPanelCategory) {

		Iterable<PanelCategory> panelCategories =
			_childPanelCategoryServiceTrackerMap.getService(
				parentPanelCategory.getKey());

		if (panelCategories == null) {
			return Collections.emptyList();
		}

		return panelCategories;
	}

	public PanelCategory getPanelCategory(String panelCategoryKey) {
		PanelCategory panelCategory =
			_panelCategoryServiceTrackerMap.getService(panelCategoryKey);

		if (panelCategory == null) {
			throw new IllegalArgumentException(
				"No panel category found with key " + panelCategoryKey);
		}

		return panelCategory;
	}

	@Activate
	protected void activate(final BundleContext bundleContext)
		throws InvalidSyntaxException {

		_childPanelCategoryServiceTrackerMap =
			ServiceTrackerMapFactory.multiValueMap(
				bundleContext, PanelCategory.class, "(panel.category=*)",
			ParentPanelCategoryServiceReferenceMapper.<PanelCategory>create());

		_childPanelCategoryServiceTrackerMap.open();

		_panelCategoryServiceTrackerMap =
			ServiceTrackerMapFactory.singleValueMap(
				bundleContext, PanelCategory.class, null,
				new ServiceReferenceMapper<String, PanelCategory>() {

					@Override
					public void map(
						ServiceReference<PanelCategory> serviceReference,
						Emitter<String> emitter) {

						PanelCategory panelCategory = bundleContext.getService(
							serviceReference);

						try {
							emitter.emit(panelCategory.getKey());
						}
						finally {
							bundleContext.ungetService(serviceReference);
						}
					}

				});

		_panelCategoryServiceTrackerMap.open();
	}

	@Deactivate
	protected void deactivate() {
		_childPanelCategoryServiceTrackerMap.close();
		_panelCategoryServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, List<PanelCategory>>
		_childPanelCategoryServiceTrackerMap;
	private ServiceTrackerMap<String, PanelCategory>
		_panelCategoryServiceTrackerMap;

}