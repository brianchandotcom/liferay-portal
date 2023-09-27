/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.bookmarks.taglib.internal.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.social.bookmarks.SocialBookmark;

import java.util.List;
import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public class SocialBookmarksTagUtil {

	public static List<DropdownItem> getDropdownItems(
		Locale locale, String[] types, String className, long classPK,
		String title, String url) {

		return new DropdownItemList() {
			{
				for (String type : types) {
					SocialBookmark socialBookmark =
						SocialBookmarksRegistryUtil.getSocialBookmark(type);

					if (socialBookmark == null) {
						continue;
					}

					add(
						dropdownItem -> {
							dropdownItem.putData("action", "post");
							dropdownItem.putData("className", className);
							dropdownItem.putData(
								"classPK", String.valueOf(classPK));
							dropdownItem.putData(
								"postURL",
								socialBookmark.getPostURL(title, url));
							dropdownItem.putData("type", type);
							dropdownItem.putData("url", url);
							dropdownItem.setLabel(
								socialBookmark.getName(locale));
						});
				}
			}
		};
	}

}