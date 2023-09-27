/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ScreenNavigationRegistry.class)
public class ScreenNavigationRegistry {

	public <T> List<ScreenNavigationCategory> getScreenNavigationCategories(
		String screenNavigationId, User user, T context) {

		List<ScreenNavigationCategory> screenNavigationCategories =
			_screenNavigationCategoriesMap.getService(screenNavigationId);

		if (ListUtil.isEmpty(screenNavigationCategories)) {
			return Collections.emptyList();
		}

		return ListUtil.filter(
			screenNavigationCategories,
			screenNavigationCategory -> ListUtil.isNotEmpty(
				getScreenNavigationEntries(
					screenNavigationCategory, user, context)));
	}

	public <T> List<ScreenNavigationEntry<T>> getScreenNavigationEntries(
		ScreenNavigationCategory screenNavigationCategory, User user,
		T context) {

		List<ScreenNavigationEntry<T>> screenNavigationEntries =
			(List<ScreenNavigationEntry<T>>)
				(List<?>)_screenNavigationEntriesMap.getService(
					_getKey(
						screenNavigationCategory.getScreenNavigationKey(),
						screenNavigationCategory.getCategoryKey()));

		if (ListUtil.isEmpty(screenNavigationEntries)) {
			return Collections.emptyList();
		}

		return ListUtil.filter(
			screenNavigationEntries,
			screenNavigationEntry -> screenNavigationEntry.isVisible(
				user, context));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_screenNavigationCategoriesMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, ScreenNavigationCategory.class, null,
				ServiceReferenceMapperFactory.createFromFunction(
					bundleContext,
					ScreenNavigationCategory::getScreenNavigationKey),
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator<>(
						"screen.navigation.category.order")));
		_screenNavigationEntriesMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext,
				(Class<ScreenNavigationEntry<?>>)
					(Class<?>)ScreenNavigationEntry.class,
				null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(screenNavigationEntry, emitter) -> emitter.emit(
						_getKey(
							screenNavigationEntry.getScreenNavigationKey(),
							screenNavigationEntry.getCategoryKey()))),
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator<>(
						"screen.navigation.entry.order")));
	}

	@Deactivate
	protected void deactivate() {
		_screenNavigationCategoriesMap.close();
		_screenNavigationEntriesMap.close();
	}

	private String _getKey(
		String screenNavigationId, String screenCategoryKey) {

		return screenNavigationId + StringPool.PERIOD + screenCategoryKey;
	}

	private ServiceTrackerMap<String, List<ScreenNavigationCategory>>
		_screenNavigationCategoriesMap;
	private ServiceTrackerMap<String, List<ScreenNavigationEntry<?>>>
		_screenNavigationEntriesMap;

}