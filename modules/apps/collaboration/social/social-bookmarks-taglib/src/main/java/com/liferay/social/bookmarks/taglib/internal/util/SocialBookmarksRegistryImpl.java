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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.social.bookmarks.SocialBookmark;
import com.liferay.social.bookmarks.SocialBookmarksRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true)
public class SocialBookmarksRegistryImpl implements SocialBookmarksRegistry {

	@Override
	public SocialBookmark getSocialBookmark(String type) {
		SocialBookmark socialBookmark = _serviceTrackerMap.getService(type);

		if (socialBookmark == null) {
			if (_isDeprecatedSocialBookmark(type)) {
				socialBookmark = new DeprecatedSocialBookmark(type);
			}
		}

		if (socialBookmark == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"Social bookmark '%s' is not available", type));
			}
		}

		return socialBookmark;
	}

	@Override
	public List<SocialBookmark> getSocialBookmarks() {
		Map<String, SocialBookmark> socialBookmarks = new HashMap<>();

		socialBookmarks.putAll(_getDeprecatedSocialBookmarks());

		for (String type : _serviceTrackerMap.keySet()) {
			socialBookmarks.put(type, getSocialBookmark(type));
		}

		return new ArrayList<>(socialBookmarks.values());
	}

	@Override
	public List<String> getSocialBookmarksTypes() {
		Set<String> socialBookmarksTypes = new LinkedHashSet<>();

		socialBookmarksTypes.addAll(_serviceTrackerMap.keySet());

		Map<String, SocialBookmark> deprecatedSocialBookmarks =
			_getDeprecatedSocialBookmarks();

		socialBookmarksTypes.addAll(deprecatedSocialBookmarks.keySet());

		return new ArrayList<>(socialBookmarksTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SocialBookmark.class, "social.bookmarks.type");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private Map<String, SocialBookmark> _getDeprecatedSocialBookmarks() {
		Map<String, SocialBookmark> oldSocialBookmarks = new HashMap<>();
		String[] oldTypes = PropsUtil.getArray(PropsKeys.SOCIAL_BOOKMARK_TYPES);

		for (String type : oldTypes) {
			if (_isValidDeprecatedSocialBookmark(type)) {
				oldSocialBookmarks.put(
					type, new DeprecatedSocialBookmark(type));
			}
		}

		return oldSocialBookmarks;
	}

	private boolean _isDeprecatedSocialBookmark(String type) {
		List<String> deprecatedTypes = Arrays.asList(
			PropsUtil.getArray(PropsKeys.SOCIAL_BOOKMARK_TYPES));

		if (deprecatedTypes.contains(type) &&
			_isValidDeprecatedSocialBookmark(type)) {

			return true;
		}

		return false;
	}

	private boolean _isValidDeprecatedSocialBookmark(String type) {
		String icon = PropsUtil.get(
			PropsKeys.SOCIAL_BOOKMARK_ICON, new Filter(type));

		String jspPath = PropsUtil.get(
			PropsKeys.SOCIAL_BOOKMARK_JSP, new Filter(type));

		String postUrl = PropsUtil.get(
			PropsKeys.SOCIAL_BOOKMARK_POST_URL, new Filter(type));

		if (Validator.isNotNull(postUrl) &&
			(Validator.isNotNull(jspPath) || Validator.isNotNull(icon))) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SocialBookmarksRegistryImpl.class);

	private ServiceTrackerMap<String, SocialBookmark> _serviceTrackerMap;

}