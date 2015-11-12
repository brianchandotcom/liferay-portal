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

package com.liferay.application.list;

import com.liferay.application.list.util.PanelCategoryServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;

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

	public List<PanelCategory> getChildPanelCategories(
		PanelCategory panelCategory) {

		return getChildPanelCategories(panelCategory.getKey());
	}

	public List<PanelCategory> getChildPanelCategories(
		PanelCategory panelCategory, final PermissionChecker permissionChecker,
		final Group group) {

		return getChildPanelCategories(
			panelCategory.getKey(), permissionChecker, group);
	}

	public List<PanelCategory> getChildPanelCategories(
		String panelCategoryKey) {

		List<PanelCategory> childPanelCategories =
			_childPanelCategoriesServiceTrackerMap.getService(panelCategoryKey);

		if (childPanelCategories == null) {
			return Collections.emptyList();
		}

		return childPanelCategories;
	}

	public List<PanelCategory> getChildPanelCategories(
		String panelCategoryKey, final PermissionChecker permissionChecker,
		final Group group) {

		List<PanelCategory> panelCategories = getChildPanelCategories(
			panelCategoryKey);

		if (panelCategories.isEmpty()) {
			return panelCategories;
		}

		return ListUtil.filter(
			panelCategories,
			new PredicateFilter<PanelCategory>() {

				@Override
				public boolean filter(PanelCategory panelCategory) {
					try {
						return panelCategory.hasAccessPermission(
							permissionChecker, group);
					}
					catch (PortalException pe) {
						_log.error(pe, pe);
					}

					return false;
				}

			});
	}

	public PanelCategory getFirstChildPanelCategory(
		String panelCategoryKey, PermissionChecker permissionChecker,
		Group group) {

		List<PanelCategory> panelCategories = getChildPanelCategories(
			panelCategoryKey);

		for (PanelCategory panelCategory : panelCategories) {
			try {
				if (panelCategory.hasAccessPermission(
						permissionChecker, group)) {

					return panelCategory;
				}
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return null;
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

		_childPanelCategoriesServiceTrackerMap =
			ServiceTrackerMapFactory.multiValueMap(
				bundleContext, PanelCategory.class, "(panel.category.key=*)",
				new PanelCategoryServiceReferenceMapper(),
				new ServiceRankingPropertyServiceReferenceComparator());

		_childPanelCategoriesServiceTrackerMap.open();

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
		_childPanelCategoriesServiceTrackerMap.close();
		_panelCategoryServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PanelCategoryRegistry.class);

	private ServiceTrackerMap<String, List<PanelCategory>>
		_childPanelCategoriesServiceTrackerMap;
	private ServiceTrackerMap<String, PanelCategory>
		_panelCategoryServiceTrackerMap;

	private static class ServiceRankingPropertyServiceReferenceComparator
		extends PropertyServiceReferenceComparator<PanelCategory> {

		public ServiceRankingPropertyServiceReferenceComparator() {
			super("service.ranking");
		}

		@Override
		public int compare(
			ServiceReference<PanelCategory> serviceReference1,
			ServiceReference<PanelCategory> serviceReference2) {

			return -(super.compare(serviceReference1, serviceReference2));
		}

	}

}