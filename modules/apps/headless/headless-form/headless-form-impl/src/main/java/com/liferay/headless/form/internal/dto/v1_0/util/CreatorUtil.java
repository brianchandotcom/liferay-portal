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

package com.liferay.headless.form.internal.dto.v1_0.util;

import com.liferay.headless.form.dto.v1_0.Creator;
import com.liferay.headless.form.internal.dto.v1_0.CreatorImpl;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Cristina González
 */
public class CreatorUtil {

	public static Creator toCreator(Portal portal, User user) throws Exception {
		if (user == null) {
			return null;
		}

		return new CreatorImpl() {
			{
				setAdditionalName(user.getMiddleName());
				setFamilyName(user.getLastName());
				setGivenName(user.getFirstName());
				setId(user.getUserId());
				setName(user.getFullName());
				setProfileURL(
					() -> {
						if (user.getPortraitId() == 0) {
							return null;
						}

						ThemeDisplay themeDisplay = new ThemeDisplay() {
							{
								setPathImage(portal.getPathImage());
							}
						};

						return user.getPortraitURL(themeDisplay);
					});
			}
		};
	}

}