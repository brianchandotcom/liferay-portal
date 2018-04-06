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

package com.liferay.social.bookmarks;

import java.util.List;

/**
 * Provides methods to read the available social bookmarks.
 *
 * @author Alejandro Tardín
 */
public interface SocialBookmarksRegistry {

	/**
	 * Retrieves a social bookmark given its type (e.g. facebook).
	 *
	 * @param type the key identifying the social bookmark
	 * @return the social bookmark service
	 */
	public SocialBookmark getSocialBookmark(String type);

	/**
	 * Retrieves every available social bookmark.
	 *
	 * @return the list containing all the social bookmark services
	 */
	public List<SocialBookmark> getSocialBookmarks();

	/**
	 * Retrieves all the social bookmarks types available. These are the keys
	 * that identify each social bookmark.
	 *
	 * @return the list containing all the social bookmark services
	 */
	public List<String> getSocialBookmarksTypes();

}