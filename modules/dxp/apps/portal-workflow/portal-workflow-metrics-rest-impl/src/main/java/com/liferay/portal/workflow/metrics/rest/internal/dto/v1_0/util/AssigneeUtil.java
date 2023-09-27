/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Assignee;

import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * @author Rafael Praxedes
 */
public class AssigneeUtil {

	public static Assignee toAssignee(
		Language language, Portal portal, ResourceBundle resourceBundle,
		long userId, Function<Long, User> userFunction) {

		User user = userFunction.apply(userId);

		return new Assignee() {
			{
				id = userId;

				setImage(
					() -> {
						if ((user == null) || (user.getPortraitId() == 0)) {
							return null;
						}

						ThemeDisplay themeDisplay = new ThemeDisplay() {
							{
								setPathImage(portal.getPathImage());
							}
						};

						return user.getPortraitURL(themeDisplay);
					});
				setName(
					() -> {
						if (userId == -1L) {
							return language.get(resourceBundle, "unassigned");
						}
						else if (user == null) {
							return String.valueOf(userId);
						}

						return user.getFullName();
					});
			}
		};
	}

}