/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.application.list.util;

import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * Provides methods for retrieving application category instances defined by
 * {@link PanelCategory} implementations. The Application Categories Registry is
 * an OSGi component. Application categories used within the registry should
 * also be OSGi components in order to be registered.
 *
 * @author Adolfo Pérez
 */
public class PanelCategoryRegistryUtil {

	public static List<PanelCategory> getChildPanelCategories(
		PanelCategory panelCategory) {

		return getChildPanelCategories(panelCategory.getKey());
	}

	public static List<PanelCategory> getChildPanelCategories(
		PanelCategory panelCategory, PermissionChecker permissionChecker,
		Group group) {

		return getChildPanelCategories(
			panelCategory.getKey(), permissionChecker, group);
	}

	public static List<PanelCategory> getChildPanelCategories(
		String panelCategoryKey) {

		List<PanelCategory> childPanelCategories =
			_childPanelCategoriesServiceTrackerMap.getService(panelCategoryKey);

		if (childPanelCategories == null) {
			return Collections.emptyList();
		}

		return childPanelCategories;
	}

	public static List<PanelCategory> getChildPanelCategories(
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
					_log.error(portalException);
				}

				return false;
			});
	}

	public static int getChildPanelCategoriesNotificationsCount(
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
				_log.error(portalException);
			}
		}

		return count;
	}

	public static PanelCategory getFirstChildPanelCategory(
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
				_log.error(portalException);
			}
		}

		return null;
	}

	public static PanelCategory getPanelCategory(String panelCategoryKey) {
		PanelCategory panelCategory =
			_panelCategoryServiceTrackerMap.getService(panelCategoryKey);

		if (panelCategory == null) {
			throw new IllegalArgumentException(
				"No panel category found with key " + panelCategoryKey);
		}

		return panelCategory;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PanelCategoryRegistryUtil.class);

	private static final ServiceTrackerMap<String, List<PanelCategory>>
		_childPanelCategoriesServiceTrackerMap;
	private static final ServiceTrackerMap<String, PanelCategory>
		_panelCategoryServiceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			PanelCategoryRegistryUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_childPanelCategoriesServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, PanelCategory.class, null,
				new PropertyServiceReferenceMapper<>("panel.category.key"),
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator<>(
						"panel.category.order")));

		_panelCategoryServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, PanelCategory.class, null,
				ServiceReferenceMapperFactory.createFromFunction(
					bundleContext, PanelCategory::getKey));
	}

}