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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.social.bookmarks.SocialBookmark;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true)
public class SocialBookmarkUtil {

	public static SocialBookmark getSocialBookmark(String type) {
		SocialBookmark socialBookmark = _instance._serviceTrackerMap.getService(
			type);

		if (socialBookmark == null) {
			socialBookmark = _getOldSocialBookmarks().get(type);
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

	public static Collection<String> getSocialBookmarkTypes() {
		Set<String> socialBookmarkTypes = new TreeSet<>();

		socialBookmarkTypes.addAll(_getOldSocialBookmarks().keySet());
		socialBookmarkTypes.addAll(_instance._serviceTrackerMap.keySet());

		return socialBookmarkTypes;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_instance = this;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SocialBookmark.class, "social.bookmark.type");
	}

	@Deactivate
	protected void deactivate() {
		_instance = null;
	}

	private static Map<String, SocialBookmark> _getOldSocialBookmarks() {
		Map<String, SocialBookmark> oldSocialBookmarks = new HashMap<>();
		String[] oldTypes = PropsUtil.getArray(PropsKeys.SOCIAL_BOOKMARK_TYPES);

		for (String type : oldTypes) {
			oldSocialBookmarks.put(type, new DeprecatedSocialBookmark(type));
		}

		return oldSocialBookmarks;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SocialBookmarkUtil.class);

	private static SocialBookmarkUtil _instance;

	private ServiceTrackerMap<String, SocialBookmark> _serviceTrackerMap;

}