/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.util;

import com.liferay.dynamic.data.mapping.form.web.internal.layout.type.constants.DDMFormPortletLayoutTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Joao Victor Alves
 */
public class DDMLayoutUtil {

	public static String getFormLayoutURL(ThemeDisplay themeDisplay) {
		return StringBundler.concat(
			themeDisplay.getPortalURL(),
			themeDisplay.getPathFriendlyURLPublic(), "/forms/shared/-/form/");
	}

	public static boolean isSharedLayout(ThemeDisplay themeDisplay) {
		Layout layout = themeDisplay.getLayout();

		String type = layout.getType();

		return type.equals(DDMFormPortletLayoutTypeConstants.LAYOUT_TYPE);
	}

}