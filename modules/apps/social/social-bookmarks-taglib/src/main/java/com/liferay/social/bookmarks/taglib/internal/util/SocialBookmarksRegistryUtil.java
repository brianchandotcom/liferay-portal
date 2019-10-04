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

import com.liferay.social.bookmarks.SocialBookmark;
import com.liferay.social.bookmarks.SocialBookmarksRegistry;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class SocialBookmarksRegistryUtil {

	public static SocialBookmark getSocialBookmark(String type) {
		SocialBookmarksRegistry socialBookmarksRegistry =
			_socialBookmarksRegistryUtil._socialBookmarksRegistry;

		return socialBookmarksRegistry.getSocialBookmark(type);
	}

	public static List<String> getSocialBookmarksTypes() {
		SocialBookmarksRegistry socialBookmarksRegistry =
			_socialBookmarksRegistryUtil._socialBookmarksRegistry;

		return socialBookmarksRegistry.getSocialBookmarksTypes();
	}

	public static String[] getValidTypes(String[] types) {
		List<String> supportedTypes = getSocialBookmarksTypes();
		List<String> validTypes = new ArrayList<>();

		for (String type : types) {
			if (supportedTypes.contains(type)) {
				validTypes.add(type);
			}
		}

		return validTypes.toArray(new String[0]);
	}

	@Activate
	protected void activate() {
		_socialBookmarksRegistryUtil = this;
	}

	@Deactivate
	protected void deactivate() {
		_socialBookmarksRegistryUtil = null;
	}

	private static SocialBookmarksRegistryUtil _socialBookmarksRegistryUtil;

	@Reference
	private SocialBookmarksRegistry _socialBookmarksRegistry;

}