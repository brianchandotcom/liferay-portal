/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.Creator;
import com.liferay.headless.delivery.dto.v1_0.UserGroupBrief;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

/**
 * @author Cristina González
 */
public class CreatorUtil {

	public static Creator toCreator(
		DTOConverterContext dtoConverterContext, Portal portal, User user) {

		if ((user == null) || user.isGuestUser()) {
			return null;
		}

		return new Creator() {
			{
				setAdditionalName(user::getMiddleName);
				setContentType(() -> "UserAccount");
				setFamilyName(user::getLastName);
				setGivenName(user::getFirstName);
				setId(user::getUserId);
				setImage(
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
				setName(user::getFullName);
				setProfileURL(
					() -> {
						if ((dtoConverterContext == null) ||
							!dtoConverterContext.containsNestedFieldsValue(
								"profileURL")) {

							return null;
						}

						Group group = user.getGroup();

						ThemeDisplay themeDisplay = new ThemeDisplay() {
							{
								setPortalURL(StringPool.BLANK);
								setSiteGroupId(group.getGroupId());
							}
						};

						return group.getDisplayURL(themeDisplay);
					});
				setUserGroupBriefs(
					() -> {
						if (!FeatureFlagManagerUtil.isEnabled("LPS-185892") ||
							(dtoConverterContext == null) ||
							!(GetterUtil.getBoolean(
								dtoConverterContext.getAttribute(
									"userGroupBriefs")) ||
							  dtoConverterContext.containsNestedFieldsValue(
								  "userGroupBriefs"))) {

							return null;
						}

						return TransformUtil.transformToArray(
							user.getUserGroups(),
							userGroup -> new UserGroupBrief() {
								{
									setId(userGroup::getUserGroupId);
									setName(userGroup::getName);
								}
							},
							UserGroupBrief.class);
					});
			}
		};
	}

}