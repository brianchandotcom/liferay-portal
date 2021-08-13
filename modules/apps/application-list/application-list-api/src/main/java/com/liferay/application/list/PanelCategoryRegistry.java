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
import com.liferay.osgi.service.tracker.collections.ServiceTrackerMapBuilder;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
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
		PanelCategory panelCategory, PermissionChecker permissionChecker,
		Group group) {

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
		String panelCategoryKey, PermissionChecker permissionChecker,
		Group group) {

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
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
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
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
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
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
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
	protected void activate(BundleContext bundleContext) {
		_childPanelCategoriesServiceTrackerMap =
			ServiceTrackerMapBuilder.SelectorFactory.newSelector(
				bundleContext, PanelCategory.class
			).map(
				"panel.category.key"
			).collectMultiValue(
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator<>(
						"panel.category.order"))
			).build();

		_panelCategoryServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, PanelCategory.class, null,
				ServiceReferenceMapperFactory.createFromFunction(
					bundleContext, PanelCategory::getKey));
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