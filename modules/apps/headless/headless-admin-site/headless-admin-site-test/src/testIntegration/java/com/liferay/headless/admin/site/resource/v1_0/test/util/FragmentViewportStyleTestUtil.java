/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.FragmentViewportStyle;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Mikel Lorza
 */
public class FragmentViewportStyleTestUtil {

	public static FragmentViewportStyle getFragmentViewportStyle() {
		return getFragmentViewportStyle(RandomTestUtil.randomBoolean());
	}

	public static FragmentViewportStyle getFragmentViewportStyle(
		Boolean hidden) {

		FragmentViewportStyle fragmentViewportStyle =
			new FragmentViewportStyle();

		fragmentViewportStyle.setBackgroundColor(RandomTestUtil::randomString);
		fragmentViewportStyle.setBorderColor(RandomTestUtil::randomString);
		fragmentViewportStyle.setBorderRadius(RandomTestUtil::randomString);
		fragmentViewportStyle.setBorderWidth(RandomTestUtil::randomString);
		fragmentViewportStyle.setFontFamily(RandomTestUtil::randomString);
		fragmentViewportStyle.setFontSize(RandomTestUtil::randomString);
		fragmentViewportStyle.setFontWeight(RandomTestUtil::randomString);
		fragmentViewportStyle.setHeight(RandomTestUtil::randomString);
		fragmentViewportStyle.setHidden(hidden);
		fragmentViewportStyle.setMarginBottom(RandomTestUtil::randomString);
		fragmentViewportStyle.setMarginLeft(RandomTestUtil::randomString);
		fragmentViewportStyle.setMarginRight(RandomTestUtil::randomString);
		fragmentViewportStyle.setMarginTop(RandomTestUtil::randomString);
		fragmentViewportStyle.setMaxHeight(RandomTestUtil::randomString);
		fragmentViewportStyle.setMaxWidth(RandomTestUtil::randomString);
		fragmentViewportStyle.setMinHeight(RandomTestUtil::randomString);
		fragmentViewportStyle.setMinWidth(RandomTestUtil::randomString);
		fragmentViewportStyle.setOpacity(RandomTestUtil::randomString);
		fragmentViewportStyle.setOverflow(RandomTestUtil::randomString);
		fragmentViewportStyle.setPaddingBottom(RandomTestUtil::randomString);
		fragmentViewportStyle.setPaddingLeft(RandomTestUtil::randomString);
		fragmentViewportStyle.setPaddingRight(RandomTestUtil::randomString);
		fragmentViewportStyle.setPaddingTop(RandomTestUtil::randomString);
		fragmentViewportStyle.setShadow(RandomTestUtil::randomString);
		fragmentViewportStyle.setTextAlign(RandomTestUtil::randomString);
		fragmentViewportStyle.setTextColor(RandomTestUtil::randomString);
		fragmentViewportStyle.setWidth(RandomTestUtil::randomString);

		return fragmentViewportStyle;
	}

}