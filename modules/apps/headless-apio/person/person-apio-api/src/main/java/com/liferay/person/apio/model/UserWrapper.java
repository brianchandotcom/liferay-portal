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

package com.liferay.person.apio.model;

import com.liferay.apio.architect.functional.Try;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * User wrapper that includes a {@code ThemeDisplay} object to allow getting the
 * different absolute URLs.
 *
 * @author Eduardo Perez
 * @review
 */
public class UserWrapper extends com.liferay.portal.kernel.model.UserWrapper {

	/**
	 * Creates a new {@link UserWrapper}.
	 *
	 * @param  user the user being wrapped
	 * @param  themeDisplay the {@link ThemeDisplay} of the current request
	 * @review
	 */
	public UserWrapper(User user, ThemeDisplay themeDisplay) {
		super(user);

		_themeDisplay = themeDisplay;
	}

	/**
	 * Returns the user's dashboard URL.
	 *
	 * @return the user's dashboard URL
	 * @review
	 */
	public String getDashboardURL() {
		return _getGroupURL(true);
	}

	/**
	 * Returns the user's portrait URL.
	 *
	 * @return the user's portrait URL
	 * @review
	 */
	public String getPortraitURL() {
		return Try.success(
			getPortraitId()
		).filter(
			portraitId -> portraitId != 0
		).map(
			__ -> getPortraitURL(_themeDisplay)
		).orElse(
			null
		);
	}

	/**
	 * Returns the user's profile URL.
	 *
	 * @return the user's profile URL
	 * @review
	 */
	public String getProfileURL() {
		return _getGroupURL(false);
	}

	private String _getGroupURL(boolean isPrivate) {
		return Try.fromFallible(
			() -> {
				if ((isPrivate && (getPrivateLayoutsPageCount() > 0)) ||
					(!isPrivate && (getPublicLayoutsPageCount() > 0))) {

					Group userGroup = getGroup();

					return userGroup.getDisplayURL(_themeDisplay, isPrivate);
				}

				return null;
			}
		).orElse(
			null
		);
	}

	private final ThemeDisplay _themeDisplay;

}