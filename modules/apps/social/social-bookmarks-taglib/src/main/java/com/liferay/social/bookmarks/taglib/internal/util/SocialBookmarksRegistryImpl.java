/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.bookmarks.taglib.internal.util;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
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
			ServiceReference<SocialBookmark> serviceReference, String type) {
		}

		@Override
		public void removedService(
			ServiceReference<SocialBookmark> serviceReference, String type) {

			_socialBookmarks.remove(type);

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}