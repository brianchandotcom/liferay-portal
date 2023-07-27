/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import java.util.List;

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
		return _serviceTrackerList.toList();
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

	private static final Log _log = LogFactoryUtil.getLog(
		SocialBookmarksRegistryImpl.class);

	private ServiceTrackerList<String> _serviceTrackerList;
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