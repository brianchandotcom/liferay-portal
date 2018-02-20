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

package com.liferay.social.taglib.internal.api;

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
			socialBookmark = _instance._oldSocialBookmarks.get(type);
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

	public static Collection<SocialBookmark> getSocialBookmarks() {
		Map<String, SocialBookmark> socialBookmarksMap = new HashMap<>();

		for (SocialBookmark socialBookmark :
				_instance._oldSocialBookmarks.values()) {

			socialBookmarksMap.put(socialBookmark.getType(), socialBookmark);
		}

		for (SocialBookmark socialBookmark :
				_instance._serviceTrackerMap.values()) {

			socialBookmarksMap.put(socialBookmark.getType(), socialBookmark);
		}

		return socialBookmarksMap.values();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_instance = this;

		String[] oldTypes = PropsUtil.getArray(PropsKeys.SOCIAL_BOOKMARK_TYPES);

		for (String type : oldTypes) {
			_oldSocialBookmarks.put(
				type, new BackwardsCompatibilitySocialBookmark(type));
		}

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SocialBookmark.class, null,
			(serviceReference, emitter) -> {
				SocialBookmark socialBookmark = bundleContext.getService(
					serviceReference);

				try {
					emitter.emit(socialBookmark.getType());
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_instance = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SocialBookmarkUtil.class);

	private static SocialBookmarkUtil _instance;

	private final Map<String, SocialBookmark> _oldSocialBookmarks =
		new HashMap<>();
	private ServiceTrackerMap<String, SocialBookmark> _serviceTrackerMap;

}