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
		return new FragmentViewportStyle() {
			{
				setBackgroundColor(RandomTestUtil::randomString);
				setBorderColor(RandomTestUtil::randomString);
				setBorderRadius(RandomTestUtil::randomString);
				setBorderWidth(RandomTestUtil::randomString);
				setFontFamily(RandomTestUtil::randomString);
				setFontSize(RandomTestUtil::randomString);
				setFontWeight(RandomTestUtil::randomString);
				setHeight(RandomTestUtil::randomString);
				setHidden(RandomTestUtil::randomBoolean);
				setMarginBottom(RandomTestUtil::randomString);
				setMarginLeft(RandomTestUtil::randomString);
				setMarginRight(RandomTestUtil::randomString);
				setMarginTop(RandomTestUtil::randomString);
				setMaxHeight(RandomTestUtil::randomString);
				setMaxWidth(RandomTestUtil::randomString);
				setMinHeight(RandomTestUtil::randomString);
				setMinWidth(RandomTestUtil::randomString);
				setOpacity(RandomTestUtil::randomString);
				setOverflow(RandomTestUtil::randomString);
				setPaddingBottom(RandomTestUtil::randomString);
				setPaddingLeft(RandomTestUtil::randomString);
				setPaddingRight(RandomTestUtil::randomString);
				setPaddingTop(RandomTestUtil::randomString);
				setShadow(RandomTestUtil::randomString);
				setTextAlign(RandomTestUtil::randomString);
				setTextColor(RandomTestUtil::randomString);
				setWidth(RandomTestUtil::randomString);
			}
		};
	}

}