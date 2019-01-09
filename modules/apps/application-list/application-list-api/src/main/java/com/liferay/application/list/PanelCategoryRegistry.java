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

import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.application.list.util.PanelCategoryServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Provides methods for retrieving application category instances defined by
 * {@link PanelCategory} implementations. The Application Categories Registry is
 * an OSGi component. Application categories used within the registry should
 * also be OSGi components in order to be registered.
 *
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
			panelCategory -> {
				try {
					return panelCategory.isShow(permissionChecker, group);
				}
				catch (PortalException pe) {
					_log.error(pe, pe);
				}

				return false;
			});
	}

	public int getChildPanelCategoriesNotificationsCount(
		PanelCategoryHelper panelCategoryHelper, String panelCategoryKey,
		PermissionChecker permissionChecker, Group group, User user) {

		int count = 0;

		for (PanelCategory panelCategory :
				getChildPanelCategories(panelCategoryKey)) {

			int notificationsCount = panelCategory.getNotificationsCount(
				panelCategoryHelper, permissionChecker, group, user);

			try {
				if ((notificationsCount > 0) &&
					panelCategory.isShow(permissionChecker, group)) {

					count += notificationsCount;
				}
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return count;
	}

	public PanelCategory getFirstChildPanelCategory(
		String panelCategoryKey, PermissionChecker permissionChecker,
		Group group) {

		List<PanelCategory> panelCategories = getChildPanelCategories(
			panelCategoryKey);

		for (PanelCategory panelCategory : panelCategories) {
			try {
				if (panelCategory.isShow(permissionChecker, group)) {
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
	protected void activate(final BundleContext bundleContext) {
		_childPanelCategoriesServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, PanelCategory.class, "(panel.category.key=*)",
				new PanelCategoryServiceReferenceMapper(),
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator(
						"panel.category.order")));

		_panelCategoryServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
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

}