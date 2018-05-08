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
 * UserWrapper that includes a {@code ThemeDisplay} object
 *
 * @author Eduardo Perez
 */
public class UserWrapper extends com.liferay.portal.kernel.model.UserWrapper {

	public UserWrapper(User user, ThemeDisplay themeDisplay) {
		super(user);

		_themeDisplay = themeDisplay;
	}

	public String getDashboardURL() {
		return getGroupURL(true);
	}

	public String getGroupURL(boolean isPrivate) {
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

	public String getPortraitURL() {
		return Try.fromFallible(
			() -> {
				if (getPortraitId() != 0) {
					return getPortraitURL(_themeDisplay);
				}

				return null;
			}
		).orElse(
			null
		);
	}

	public String getProfileURL() {
		return getGroupURL(false);
	}

	private final ThemeDisplay _themeDisplay;

}