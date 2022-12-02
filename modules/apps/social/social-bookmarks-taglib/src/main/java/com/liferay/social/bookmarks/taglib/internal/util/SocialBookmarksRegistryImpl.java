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

package com.liferay.social.bookmarks.taglib.internal.util;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.social.bookmarks.SocialBookmark;
import com.liferay.social.bookmarks.SocialBookmarksRegistry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Alejandro Tardín
 */
@Component(service = SocialBookmarksRegistry.class)
public class SocialBookmarksRegistryImpl implements SocialBookmarksRegistry {

	@Override
	public SocialBookmark getSocialBookmark(String type) {
		SocialBookmark socialBookmark = _socialBookmarks.get(type);

		if (socialBookmark == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format("Social bookmark %s is not available", type));
			}
		}

		return socialBookmark;
	}

	@Override
	public List<String> getSocialBookmarksTypes() {
		return _serviceTrackerList.toList();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, SocialBookmark.class, "(social.bookmarks.type=*)",
			new SocialBookmarkTypeServiceTrackerCustomizer(bundleContext),
			new PropertyServiceReferenceComparator<>(
				"social.bookmarks.priority"));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SocialBookmarksRegistryImpl.class);

	private ServiceTrackerList<String> _serviceTrackerList;
	private final Map<String, SocialBookmark> _socialBookmarks =
		new ConcurrentHashMap<>();

	private class SocialBookmarkTypeServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<SocialBookmark, String> {

		public SocialBookmarkTypeServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public String addingService(
			ServiceReference<SocialBookmark> serviceReference) {

			String type = (String)serviceReference.getProperty(
				"social.bookmarks.type");

			_socialBookmarks.put(
				type, _bundleContext.getService(serviceReference));

			return type;
		}

		@Override
		public void modifiedService(
			ServiceReference<SocialBookmark> serviceReference, String service) {
		}

		@Override
		public void removedService(
			ServiceReference<SocialBookmark> serviceReference, String service) {

			_socialBookmarks.remove(
				(String)serviceReference.getProperty("social.bookmarks.type"));

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}