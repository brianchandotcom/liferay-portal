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
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.social.bookmarks.SocialBookmark;
import com.liferay.social.bookmarks.SocialBookmarksRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = SocialBookmarksRegistry.class)
public class SocialBookmarksRegistryImpl implements SocialBookmarksRegistry {

	@Override
	public SocialBookmark getSocialBookmark(String type) {
		SocialBookmark socialBookmark = _serviceTrackerMap.getService(type);

		if ((socialBookmark == null) && _isDeprecatedSocialBookmark(type)) {
			socialBookmark = new DeprecatedSocialBookmark(type);
		}

		if (socialBookmark == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format("Social bookmark %s is not available", type));
			}
		}

		return socialBookmark;
	}

	@Override
	public List<SocialBookmark> getSocialBookmarks() {
		List<SocialBookmark> socialBookmarks = new ArrayList<>();

		for (String type : getSocialBookmarksTypes()) {
			socialBookmarks.add(getSocialBookmark(type));
		}

		return socialBookmarks;
	}

	@Override
	public List<String> getSocialBookmarksTypes() {
		Set<String> socialBookmarksTypes = new LinkedHashSet<>();

		for (String type : _serviceTrackerList) {
			socialBookmarksTypes.add(type);
		}

		for (String type : PropsUtil.getArray(_SOCIAL_BOOKMARK_TYPES)) {
			if (_isValidDeprecatedSocialBookmark(type)) {
				socialBookmarksTypes.add(type);
			}
		}

		return new ArrayList<>(socialBookmarksTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, SocialBookmark.class, null,
			new SocialBookmarkTypeServiceTrackerCustomizer(),
			new PropertyServiceReferenceComparator<>(
				"social.bookmarks.priority"));

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SocialBookmark.class, "social.bookmarks.type");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
		_serviceTrackerMap.close();
	}

	private boolean _isDeprecatedSocialBookmark(String type) {
		List<String> deprecatedSocialBookmarksTypes = Arrays.asList(
			PropsUtil.getArray(_SOCIAL_BOOKMARK_TYPES));

		if (deprecatedSocialBookmarksTypes.contains(type) &&
			_isValidDeprecatedSocialBookmark(type)) {

			return true;
		}

		return false;
	}

	private boolean _isValidDeprecatedSocialBookmark(String type) {
		String icon = PropsUtil.get(_SOCIAL_BOOKMARK_ICON, new Filter(type));
		String jspPath = PropsUtil.get(_SOCIAL_BOOKMARK_JSP, new Filter(type));
		String postUrl = PropsUtil.get(
			_SOCIAL_BOOKMARK_POST_URL, new Filter(type));

		if (Validator.isNotNull(postUrl) &&
			(Validator.isNotNull(icon) || Validator.isNotNull(jspPath))) {

			return true;
		}

		return false;
	}

	private static final String _SOCIAL_BOOKMARK_ICON = "social.bookmark.icon";

	private static final String _SOCIAL_BOOKMARK_JSP = "social.bookmark.jsp";

	private static final String _SOCIAL_BOOKMARK_POST_URL =
		"social.bookmark.post.url";

	private static final String _SOCIAL_BOOKMARK_TYPES =
		"social.bookmark.types";

	private static final Log _log = LogFactoryUtil.getLog(
		SocialBookmarksRegistryImpl.class);

	private ServiceTrackerList<SocialBookmark, String> _serviceTrackerList;
	private ServiceTrackerMap<String, SocialBookmark> _serviceTrackerMap;

	private static class SocialBookmarkTypeServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<SocialBookmark, String> {

		@Override
		public String addingService(
			ServiceReference<SocialBookmark> serviceReference) {

			return (String)serviceReference.getProperty(
				"social.bookmarks.type");
		}

		@Override
		public void modifiedService(
			ServiceReference<SocialBookmark> serviceReference, String service) {
		}

		@Override
		public void removedService(
			ServiceReference<SocialBookmark> serviceReference, String service) {
		}

	}

}