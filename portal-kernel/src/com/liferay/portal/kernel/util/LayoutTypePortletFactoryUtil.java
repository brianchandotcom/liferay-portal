/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;

/**
 * @author Raymond Augé
 */
public class LayoutTypePortletFactoryUtil {

	public static LayoutTypePortlet create(Layout layout) {
		return _layoutTypePortletFactory.create(layout);
	}

	public static LayoutTypePortletFactory getLayoutTypePortletFactory() {
		return _layoutTypePortletFactory;
	}

	public void setLayoutTypePortletFactory(
		LayoutTypePortletFactory layoutTypePortletFactory) {

		_layoutTypePortletFactory = layoutTypePortletFactory;
	}

	private static LayoutTypePortletFactory _layoutTypePortletFactory;

}