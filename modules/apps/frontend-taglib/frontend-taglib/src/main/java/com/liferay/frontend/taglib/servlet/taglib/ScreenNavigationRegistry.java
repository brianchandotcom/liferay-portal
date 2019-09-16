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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ScreenNavigationRegistry.class)
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
			screenNavigationCategory -> {
				List<ScreenNavigationEntry> screenNavigationEntries =
					getScreenNavigationEntries(
						screenNavigationCategory, user, context);

				return ListUtil.isNotEmpty(screenNavigationEntries);
			});
	}

	public <T> List<ScreenNavigationEntry> getScreenNavigationEntries(
		ScreenNavigationCategory screenNavigationCategory, User user,
		T context) {

		String key = _getKey(
			screenNavigationCategory.getScreenNavigationKey(),
			screenNavigationCategory.getCategoryKey());

		List<ScreenNavigationEntry> screenNavigationEntries =
			_screenNavigationEntriesMap.getService(key);

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
		_bundleContext = bundleContext;

		_screenNavigationCategoriesMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, ScreenNavigationCategory.class, null,
				new ScreenNavigationCategoriesServiceReferenceMapper(),
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator(
						"screen.navigation.category.order")));
		_screenNavigationEntriesMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, ScreenNavigationEntry.class, null,
				new ScreenNavigationEntriesServiceReferenceMapper(),
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator(
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

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, List<ScreenNavigationCategory>>
		_screenNavigationCategoriesMap;
	private ServiceTrackerMap<String, List<ScreenNavigationEntry>>
		_screenNavigationEntriesMap;

	private class ScreenNavigationCategoriesServiceReferenceMapper
		implements ServiceReferenceMapper<String, ScreenNavigationCategory> {

		@Override
		public void map(
			ServiceReference<ScreenNavigationCategory> serviceReference,
			Emitter<String> emitter) {

			ScreenNavigationCategory screenNavigationCategory =
				_bundleContext.getService(serviceReference);

			try {
				emitter.emit(screenNavigationCategory.getScreenNavigationKey());
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

	}

	private class ScreenNavigationEntriesServiceReferenceMapper
		implements ServiceReferenceMapper<String, ScreenNavigationEntry> {

		@Override
		public void map(
			ServiceReference<ScreenNavigationEntry> serviceReference,
			Emitter<String> emitter) {

			ScreenNavigationEntry screenNavigationEntry =
				_bundleContext.getService(serviceReference);

			try {
				String key = _getKey(
					screenNavigationEntry.getScreenNavigationKey(),
					screenNavigationEntry.getCategoryKey());

				emitter.emit(key);
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

	}

}