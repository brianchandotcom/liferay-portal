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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alejandro Tardín
 */
public class SocialBookmarksRegistryUtil {

	public static SocialBookmark getSocialBookmark(String type) {
		return _socialBookmarksRegistry.getSocialBookmark(type);
	}

	public static List<String> getSocialBookmarksTypes() {
		return _socialBookmarksRegistry.getSocialBookmarksTypes();
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

	public static void setSocialBookmarksRegistry(
		SocialBookmarksRegistry socialBookmarksRegistry) {

		_socialBookmarksRegistry = socialBookmarksRegistry;
	}

	private static SocialBookmarksRegistry _socialBookmarksRegistry;

}