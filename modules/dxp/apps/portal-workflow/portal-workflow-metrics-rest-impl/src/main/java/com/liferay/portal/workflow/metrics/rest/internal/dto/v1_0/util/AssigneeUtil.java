/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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